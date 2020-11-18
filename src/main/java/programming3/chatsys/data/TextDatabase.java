package programming3.chatsys.data;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class TextDatabase implements Database {
    private final File messageDb;
    private final File userDb;

    public TextDatabase(String messageFilename, String userFilename) {
        this.messageDb = new File(messageFilename);
        this.userDb = new File(userFilename);
    }

    public TextDatabase(File messagesFile, File usersFile) {
        this.messageDb = messagesFile;
        this.userDb = usersFile;
    }

    /**
     * Reads user from the database file
     * @return a map of Users which keys are the usernames
     * @throws FileNotFoundException if the user database file doesn't exists
     */
    public Map<String, User> readUsers() {
        Map<String, User> users = new HashMap<String, User>();
        try(BufferedReader reader = new BufferedReader(new FileReader(this.userDb))) {
            for(String line: reader.lines().collect(Collectors.toList())) {
                User u = new User(line);
                users.put(u.getUserName(), u);
            }
        } catch(IOException e) {
        }
        return users;
    }

    /**
     * Reads chat messages from the database file
     * @return the list of ChatMessages
     * @throws FileNotFoundException if the message database file doesn't exists
     */
    public List<ChatMessage> readMessages() {
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
        try(BufferedReader reader = new BufferedReader(new FileReader(this.messageDb))) {
            for(String line: reader.lines().collect(Collectors.toList())) {
                ChatMessage m = new ChatMessage(line);
                messages.add(m);
            }
        } catch(IOException e) {
        }
        return messages;
    }

    @Override
    public List<ChatMessage> readMessages(int n) throws SQLException {
        return null;
    }

    /**
     * Adds a ChatMessage to the database.
     * @param message the message to add
     */
    public ChatMessage addMessage(String userName, String message) {
        ChatMessage chatMessage = new ChatMessage(this.lastId() + 1, userName, message);
        chatMessage.save(this.messageDb);
        return chatMessage;
    }

    /**
     * Returns the last id used in the message database.
     * @return the last id or 0 if the database is empty or doesn't exists.
     */
    public int lastId() {
        return this.lastId(this.readMessages());
    }

    /**
     * Returns the last id used in a list of messages.
     * @param messages The messages.
     * @return the last id or 0 if the database is empty or doesn't exists.
     */
    private int lastId(List<ChatMessage> messages) {
        try {
            return messages.stream().max(Comparator.comparing(ChatMessage::getId)).get().getId();
        } catch(NoSuchElementException e) {
            return 0;
        }
    }

    /**
     * Check whether a pair of username and password are valid
     * @param userName the name of the user
     * @param password the password of the user
     * @return true if the username and password are valid, false otherwise
     */
    public boolean authenticate(String userName, String password) {
        User user = this.readUsers().get(userName);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Add a user to the database if it is not yet inside
     * @param user the user to add
     */
    public boolean register(User user) {
        Map<String, User> users = this.readUsers();
        if (users.get(user.getUserName()) != null) {
            return false;
        } else {
            user.save(this.userDb);
            return true;
        }
    }

    /**
     * Gets unread messages for a user and updates the user's last read id in the DB
     * @param userName user
     * @return Unread messages for the user
     */
    public List<ChatMessage> getUnreadMessages(String userName) {
        List<ChatMessage> messages = this.readMessages();
        final int lastReadId = this.readUsers().get(userName).getLastReadId();
        messages = messages.stream().filter(m -> m.getId() > lastReadId).collect(Collectors.toList());
        if (messages.size() > 0) {
            this.updateLastReadId(userName, messages);
        }
        return messages;
    }

    void updateLastReadId(String userName, List<ChatMessage> messages) {
        if (messages.size() > 0) {
            this.updateLastReadId(userName, this.lastId(messages));
        }
    }

    public void updateLastReadId(String userName, int id) {
        Map<String, User> users = this.readUsers();
        users.get(userName).setLastReadId(id);
        this.rewriteUsers(users);
    }

    public void rewriteUsers(Map<String, User> users) {
        this.userDb.delete();
        for (User user: users.values()) {
            user.save(this.userDb);
        }
    }
}
