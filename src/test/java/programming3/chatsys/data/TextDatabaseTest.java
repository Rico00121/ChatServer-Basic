package programming3.chatsys.data;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
class TextDatabaseTest {

    private static Database database;
    @BeforeAll
    public static void init(){
        System.out.println("初始化数据");
        database=new SecureTextDatabase("messages.db","users.db");
    }
    @Test
    public void readUsers() {
        System.out.println(database.readUsers());
    }

    @Test
    void readMessages() {
        System.out.println(database.readMessages());
    }

    @Test
    void testReadMessages() {
        System.out.println(database.readMessages(5));
    }

    @Test
    void addMessage() {
        System.out.println(database.addMessage("user_2","Hello,world!"));
    }

    @Test
    void authenticate() {
        System.out.println(database.authenticate("Rico","123456cs"));
    }

    @Test
    void register(){
        User user=new User("user_2","Full Name","PassWord");
        System.out.println(database.register(user));
    }

    @Test
    void getUnreadMessages() throws SQLException {
        System.out.println(database.getUnreadMessages("user_2"));
    }
}