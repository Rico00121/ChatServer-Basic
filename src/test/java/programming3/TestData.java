package programming3;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.File;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class TestData {
    public static final File MESSAGES_DB = new File("messages_test.db");
    public static final File USERS_DB = new File("users_test.db");

    public static final User user1 = new User("maelick", "Maëlick", "mypassword");
    public static final User user2 = new User("johndoe", "John Doe", "thisissecret");

    public static final ChatMessage message1 = new ChatMessage(1, "maelick", 0, "Hello World!");
    public static final ChatMessage message2 = new ChatMessage(2, "johndoe", 30000, "Ping!");
    public static final ChatMessage message3 = new ChatMessage(3,"maelick", 60000, "Pong!");

    private static void clean(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Removes test databases if they exist
     */
    public static void clean() {
        clean(MESSAGES_DB);
        clean(USERS_DB);
    }

    /**
     * Setup test database with test data
     */
    public static void setup() {
        user1.save(USERS_DB);
        user2.save(USERS_DB);
        message1.save(MESSAGES_DB);
        message2.save(MESSAGES_DB);
        message3.save(MESSAGES_DB);
    }
}
