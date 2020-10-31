package programming3.chatsys.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import programming3.TestData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
class ChatMessageTest {

    @Test
    void parse() {
        ChatMessage expected = new ChatMessage(1, "maelick", 0, "My message");
        ChatMessage actual = new ChatMessage("1\tmaelick\t0\tMy message");
        assertEquals(expected, actual);
    }

    @Test
    void format() {
        String expected = "1\tmaelick\t0\tMy message";
        ChatMessage actual = new ChatMessage(1, "maelick", 0, "My message");
        assertEquals(expected, actual.format());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        expected = "1\tmaelick\t" + timestamp.getTime() + "\tMy message";
        actual = new ChatMessage(1, "maelick", timestamp, "My message");
        assertEquals(expected, actual.format());
    }

    @Test
    void parseFormat() {
        ChatMessage message = new ChatMessage(1, "maelick", "My message");
        assertEquals(message, new ChatMessage(message.format()));
    }

    @Test
    void save() throws IOException {
        clean();
        ChatMessage message = new ChatMessage(1, "maelick", "My message");
        message.save(TestData.MESSAGES_DB);
        message.save(TestData.MESSAGES_DB);
        try(BufferedReader reader = new BufferedReader(new FileReader(TestData.MESSAGES_DB))) {
            assertEquals(new ChatMessage(reader.readLine()), message);
            assertEquals(new ChatMessage(reader.readLine()), message);
            assertNull(reader.readLine());
        } catch(IOException e) {
            throw e;
        }
    }

    @AfterAll
    static void clean() {
        TestData.clean();
    }

    @Test
    void testInvalidAttributes() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ChatMessage(1, "Maelick 42", "Test message");
        }, "userName is invalid");
        assertThrows(IllegalArgumentException.class, () -> {
            new ChatMessage(1, "maelick", "Test\nmessage");
        }, "message contains a line feed");
    }

    @Test
    void testInvalidParse() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ChatMessage("maelick\t0\tTest message");
        }, "The String does not contain enough tabulations and cannot be parsed");
        assertThrows(IllegalArgumentException.class, () -> {
            new ChatMessage("0\tMaelick 42\t0\tTest message");
        }, "userName is invalid");
        assertThrows(IllegalArgumentException.class, () -> {
            new ChatMessage("0\tmaelick\t0\tTest\nmessage");
        }, "message contains a line feed");
    }

}