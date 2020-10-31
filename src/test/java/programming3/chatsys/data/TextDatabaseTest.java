package programming3.chatsys.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programming3.TestData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
class TextDatabaseTest {

    private static final TextDatabase db = new TextDatabase(TestData.MESSAGES_DB, TestData.USERS_DB);

    @Test
    void testReadUsers() {
        Map<String, User> users = db.readUsers();
        assertEquals(TestData.user1, users.get("maelick"));
        assertEquals(TestData.user2, users.get("johndoe"));
        assertEquals(2, users.size());
    }

    @Test
    void testReadMessages() {
        List<ChatMessage> messages = db.readMessages();
        assertEquals(TestData.message1, messages.get(0));
        assertEquals(TestData.message2, messages.get(1));
        assertEquals(TestData.message3, messages.get(2));
        assertEquals(3, messages.size());
    }

    @Test
    void testAuthenticate() {
        assertTrue(db.authenticate("maelick", "mypassword"));
        assertFalse(db.authenticate("maelick", "wrongpassword"));
        assertFalse(db.authenticate("janedoe", "nopassword"));
    }

    @Test
    void testRewriteUser() {
        Map<String, User> users = db.readUsers();
        users.remove(TestData.user1.getUserName());
        db.rewriteUsers(users);
        users = db.readUsers();
        assertEquals(1, users.size());
        assertEquals(TestData.user2, users.get(TestData.user2.getUserName()));
    }

    @Test
    void testUpdateLastReadId() {
        db.updateLastReadId(TestData.user1.getUserName(), Arrays.asList(new ChatMessage[]{TestData.message1}.clone()));
        assertEquals(1, db.readUsers().get(TestData.user1.getUserName()).getLastReadId());
        db.updateLastReadId(TestData.user1.getUserName(), 2);
        assertEquals(2, db.readUsers().get(TestData.user1.getUserName()).getLastReadId());
    }

    @Test
    void getUnreadMessages() {
        assertEquals(3, db.getUnreadMessages(TestData.user1.getUserName()).size());
        assertEquals(0, db.getUnreadMessages(TestData.user1.getUserName()).size());
        assertEquals(3, db.getUnreadMessages(TestData.user2.getUserName()).size());
        assertEquals(0, db.getUnreadMessages(TestData.user2.getUserName()).size());
        db.updateLastReadId(TestData.user1.getUserName(), 1);
        assertEquals(2, db.getUnreadMessages(TestData.user1.getUserName()).size());
        assertEquals(0, db.getUnreadMessages(TestData.user2.getUserName()).size());
        assertEquals(0, db.getUnreadMessages(TestData.user1.getUserName()).size());
        db.updateLastReadId(TestData.user1.getUserName(), 10);
        assertEquals(0, db.getUnreadMessages(TestData.user1.getUserName()).size());
        assertEquals(0, db.getUnreadMessages(TestData.user2.getUserName()).size());
    }

    @Test
    void addMessageTest() {
        int newId = db.lastId() + 1;
        ChatMessage message = db.addMessage("test", "test");
        assertEquals(newId, message.getId());
        assertEquals(newId, db.lastId());
    }

    @AfterEach
    void clean() {
        TestData.clean();
    }

    @BeforeEach
    void setup() {
        clean();
        TestData.setup();
    }
}