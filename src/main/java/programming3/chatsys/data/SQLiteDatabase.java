package programming3.chatsys.data;

import java.sql.*;
import java.util.*;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class SQLiteDatabase implements Database {
    protected Connection connection;

    public SQLiteDatabase(String dbFileName){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
            createChatMessageTable();
            createUserTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    private void createUserTable(){
        String query="CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY," +
                "username text UNIQUE NOT NULL,fullname text NOT NULL," +
                "password text NOT NULL,last_read_id integer DEFAULT 0);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    private void createChatMessageTable(){
        String query="CREATE TABLE IF NOT EXISTS chatmessages (id integer PRIMARY KEY,user integer NOT NULL,\n" +
                "time integer NOT NULL,message text NOT NULL);";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
    public Map<String, User> readUsers(){
        String query="SELECT * FROM users";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            ResultSet resultSet=statement.executeQuery();
            Map<String,User> userMap=new HashMap<>();
            while(resultSet.next()){
                User user=new User(resultSet.getString("username"),
                        resultSet.getString("fullname"),
                        resultSet.getString("password"),
                        resultSet.getInt("last_read_id"));
                userMap.put(user.getUserName(),user);
            }
            resultSet.close();
            return userMap;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public List<ChatMessage> readMessages(){
        String query="SELECT chatmessages.id, username, time, message FROM chatmessages, users WHERE users.id = user";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<ChatMessage> messages = new LinkedList<>();
            while (resultSet.next()) {
                ChatMessage chatMessage = new ChatMessage(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getLong("time"),
                        resultSet.getString("message"));
                messages.add(chatMessage);
            }
            return messages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    @Override
    public List<ChatMessage> readMessages(int n){
        String query="SELECT * FROM (SELECT chatmessages.id, username, time, message FROM chatmessages, users WHERE users.id = user ORDER BY chatmessages.id DESC LIMIT ?) ORDER BY id ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, n);
            ResultSet resultSet = statement.executeQuery();
            List<ChatMessage> messages = new LinkedList<>();
            while (resultSet.next()) {
                ChatMessage chatMessage = new ChatMessage(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getLong("time"),
                        resultSet.getString("message"));
                messages.add(chatMessage);
            }
            return messages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        String query="INSERT INTO chatmessages(user, time, message) SELECT id, ?, ? FROM users WHERE username = ?";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setLong(1, System.currentTimeMillis());
            statement.setString(2,message);
            statement.setString(3,userName);
            statement.executeUpdate();
            ChatMessage chatMessage=new ChatMessage(this.lastId()+1,userName,message);
            return chatMessage;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public int lastId(){
        return this.lastId(this.readMessages());
    }
    /**
     * @author Maelick Claes (maelick.claes@oulu.fi)
     */
    private int lastId(List<ChatMessage> messages) {
        try {
            return messages.stream().max(Comparator.comparing(ChatMessage::getId)).get().getId();
        } catch(NoSuchElementException e) {
            return 0;
        }
    }


    @Override
    public boolean authenticate(String userName, String password)  {
        String query="SELECT COUNT(*) FROM users WHERE username = ? AND password = ? ";
        try {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1,userName);
            statement.setString(2,password);
            ResultSet resultSet=statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;

        }

    }

    @Override
    public boolean register(User user)   {
        String query="INSERT INTO users(username, fullname, password) VALUES(?, ?, ?)";
        try{
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setString(1,user.getUserName());
        statement.setString(2,user.getFullName());
        statement.setString(3,user.getPassword());
        return statement.executeUpdate()==1;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName){
        String query="SELECT chatmessages.id, u1.username, time, message FROM chatmessages, users u1, users u2 " +
                "WHERE u1.id = user AND u2.username = ? AND u2.last_read_id < chatmessages.id";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            List<ChatMessage> unreadMessages = new LinkedList<>();
            while (resultSet.next()) {
                ChatMessage chatMessage = new ChatMessage(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getLong("time"),
                        resultSet.getString("message"));
                unreadMessages.add(chatMessage);
            }
            resultSet.close();
            if (!unreadMessages.isEmpty()) {
                updateLastReadId(userName, this.lastId(unreadMessages));
            }

            return unreadMessages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    private void updateLastReadId(String userName, int id) throws SQLException {
        String query2 = "UPDATE users SET last_read_id = ? WHERE username = ?";
        PreparedStatement statement2 = connection.prepareStatement(query2);
        statement2.setInt(1, id);
        statement2.setString(2, userName);
        statement2.executeUpdate();
    }

}
