package programming3.chatsys.data;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDatabaseTest {
    private static SQLiteDatabase database;
    @BeforeAll
    public static void init(){
        System.out.println("初始化数据");
        database=new SQLiteDatabase("chatsys.sqlite");
    }
    @AfterAll
    public static void finish(){
        database.closeConnection();
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
        System.out.println(database.addMessage("Rico","Hello,world!"));
    }

    @Test
    void authenticate() {
        System.out.println(database.authenticate("Rico","123456cs"));
    }

    @Test
    void register(){
        User user=new User("Rico2","Rico_Tea2","123456cs");
        System.out.println(database.register(user));
    }

    @Test
    void getUnreadMessages() throws SQLException {
        System.out.println(database.getUnreadMessages("Rico"));
    }
}