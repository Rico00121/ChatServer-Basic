package programming3.chatsys.data;

import java.sql.*;
import java.util.*;

public class SQLiteDatabase implements Database {
    protected Connection connection;

    public SQLiteDatabase(String dbFileName) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
        createChatMessageTable();
        createUserTable();
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }
    private void createUserTable() throws SQLException {
        String query="CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY," +
                "username text UNIQUE NOT NULL,fullname text NOT NULL," +
                "password text NOT NULL,last_read_id integer DEFAULT 0);";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.execute();
        statement.close();


    }
    private void createChatMessageTable() throws SQLException {
        String query="CREATE TABLE IF NOT EXISTS chatmessages (id integer PRIMARY KEY,user integer NOT NULL,\n" +
                "time integer NOT NULL,message text NOT NULL);";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.execute();
        statement.close();
    }
    @Override
    public Map<String, User> readUsers() throws SQLException {
        String query="SELECT * FROM users";
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
    }

    @Override
    public List<ChatMessage> readMessages() throws SQLException {
        String query="SELECT chatmessages.id, username, time, message FROM chatmessages, users WHERE users.id = user";
        PreparedStatement statement=connection.prepareStatement(query);
        ResultSet resultSet=statement.executeQuery();
        List<ChatMessage> messages = new LinkedList<>();
        while (resultSet.next()){
            ChatMessage chatMessage=new ChatMessage(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getLong("time"),
                    resultSet.getString("message"));
            messages.add(chatMessage);
        }
        return messages;
    }
    @Override
    public List<ChatMessage> readMessages(int n) throws SQLException {
        String query="SELECT * FROM (SELECT chatmessages.id, username, time, message FROM chatmessages, users WHERE users.id = user ORDER BY chatmessages.id DESC LIMIT ?) ORDER BY id ASC";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setInt(1,n);
        ResultSet resultSet=statement.executeQuery();
        List<ChatMessage> messages = new LinkedList<>();
        while (resultSet.next()){
            ChatMessage chatMessage=new ChatMessage(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getLong("time"),
                    resultSet.getString("message"));
            messages.add(chatMessage);
        }
        return messages;
    }

    @Override
    public ChatMessage addMessage(String userName, String message) throws SQLException {
        String query="INSERT INTO chatmessages(user, time, message) SELECT id, ?, ? FROM users WHERE username = ?";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setLong(1, System.currentTimeMillis());
        statement.setString(2,message);
        statement.setString(3,userName);
        statement.executeUpdate();
        ChatMessage chatMessage=new ChatMessage(this.lastId()+1,userName,message);
        return chatMessage;
    }

    @Override
    public int lastId() throws SQLException {
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
    public boolean authenticate(String userName, String password) throws SQLException {
        String query="SELECT COUNT(*) FROM users WHERE username = ? AND password = ? ";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setString(1,userName);
        statement.setString(2,password);
        ResultSet resultSet=statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) == 1;
        } else {
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
    public List<ChatMessage> getUnreadMessages(String userName) throws SQLException {
        String query="SELECT chatmessages.id, u1.username, time, message FROM chatmessages, users u1, users u2 " +
                "WHERE u1.id = user AND u2.username = ? AND u2.last_read_id < chatmessages.id";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setString(1,userName);
        ResultSet resultSet=statement.executeQuery();
        List<ChatMessage> unreadMessages = new LinkedList<>();
        while (resultSet.next()){
            ChatMessage chatMessage=new ChatMessage(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getLong("time"),
                    resultSet.getString("message"));
            unreadMessages.add(chatMessage);
        }
        resultSet.close();
        if (!unreadMessages.isEmpty()){
            String query2="UPDATE users SET last_read_id = ? WHERE username = ?";
            PreparedStatement statement2=connection.prepareStatement(query2);
            statement2.setInt(1,this.lastId(unreadMessages));
            statement2.setString(2,userName);
            statement2.executeUpdate();
        }

        return unreadMessages;

    }

}
