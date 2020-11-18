package programming3.chatsys.data;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDatabaseTest {
    private static SQLiteDatabase database;
    @BeforeAll
    public static void init() throws SQLException {
        System.out.println("初始化数据");
        database=new SQLiteDatabase("test.sqlite");
    }
    @Test public void readUsers() {
        try {
            System.out.println(database.readUsers());
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void readMessages() {
        try {
            System.out.println(database.readMessages());
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void testReadMessages() {
        try {
            System.out.println(database.readMessages(5));
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void addMessage() {
        try {
            System.out.println(database.addMessage("Rico","Hello,world!"));
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void authenticate() {
        try {
            System.out.println(database.authenticate("Rico","123456cs"));
            database.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void register() throws SQLException {
            User user=new User("Rico2","Rico_Tea2","123456cs");
            System.out.println(database.register(user));
            database.closeConnection();
    }

    @Test
    void getUnreadMessages() throws SQLException {

        System.out.println(database.getUnreadMessages("Rico"));
        database.closeConnection();
    }
}