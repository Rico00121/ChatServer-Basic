package programming3.chatsys.data;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public interface Database {
    /**
     * Reads user from the database file
     * @return a map of Users which keys are the usernames
     * @throws FileNotFoundException if the user database file doesn't exists
     */
    Map<String, User> readUsers();

    /**
     * Reads chat messages from the database file
     * @return the list of ChatMessages
     * @throws FileNotFoundException if the message database file doesn't exists
     */
    List<ChatMessage> readMessages();

    /**
     * Adds a ChatMessage to the database.
     * @param userName user who sends the message.
     * @param message the message to add.
     */
    ChatMessage addMessage(String userName, String message);

    /**
     * Returns the last id used in the message database.
     * @return the last id or 0 if the database is empty or doesn't exists.
     */
    int lastId();

    /**
     * Check whether a pair of username and password are valid
     * @param userName the name of the user
     * @param password the password of the user
     * @return true if the username and password are valid, false otherwise
     */
    boolean authenticate(String userName, String password);

    /**
     * Add a user to the database if it is not yet inside
     * @param user the user to add
     */
    boolean register(User user);

    /**
     * Gets unread messages for a user and updates the user's last read id in the DB
     * @param userName user
     * @return Unread messages for the user
     */
    List<ChatMessage> getUnreadMessages(String userName);
}
