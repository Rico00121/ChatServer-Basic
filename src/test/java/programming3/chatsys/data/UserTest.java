package programming3.chatsys.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import programming3.TestData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
class UserTest {

    @Test
    void testParse() {
        User expected = new User("maelick", "Maëlick", "mypassword");
        User actual = new User("maelick\tMaëlick\tmypassword\t0");
        assertEquals(expected, actual);
    }

    @Test
    void testFormat() {
        String expected = "maelick\tMaëlick\tmypassword\t0";
        User actual = new User("maelick", "Maëlick", "mypassword");
        assertEquals(expected, actual.format());
    }

    @Test
    void testParseFormat() {
        User user = new User("maelick", "Maëlick", "mypassword");
        assertEquals(user, new User(user.format()));
    }

    @Test
    void testSave() throws IOException {
        clean();
        User user = new User("maelick", "Maëlick", "mypassword");
        user.save(TestData.USERS_DB);
        user.save(TestData.USERS_DB);
        try(BufferedReader reader = new BufferedReader(new FileReader(TestData.USERS_DB))) {
            assertEquals(new User(reader.readLine()), user);
            assertEquals(new User(reader.readLine()), user);
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
    void testCheckUsername() {
        assertTrue(User.userNameIsValid("Maelick_42"));
        assertFalse(User.userNameIsValid("Maelick 42"));
        assertFalse(User.userNameIsValid("Maelick\n42"));
        assertFalse(User.userNameIsValid("Maëlick"));
    }

    @Test
    void testInvalidAttributes() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Maelick 42", "Maelick", "password");
        }, "userName is invalid");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("maelick", "Maelick\nClaes", "password");
        }, "fullName contains a line feed");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("maelick", "Maelick", "my\npassword");
        }, "password contains a line feed");
    }

    @Test
    void testInvalidParse() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("maelick\tMaelick\tpassword");
        }, "The String does not contain enough tabulations and cannot be parsed");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("Maelick 42\tMaelick\tpassword\t0");
        }, "userName is invalid");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("maelick\tMaelick\nClaes\tpassword\t0");
        }, "fullName contains a line feed");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("maelick\tMaelick\tmy\npassword\t0");
        }, "password contains a line feed");
    }
}