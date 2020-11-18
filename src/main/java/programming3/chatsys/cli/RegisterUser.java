package programming3.chatsys.cli;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.TextDatabase;
import programming3.chatsys.data.User;

import java.sql.SQLException;

/**
 * Main program for user registration. Takes the username, user full name and the password as command line arguments.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class RegisterUser {
    public static void main(String[] args) throws SQLException {
        Database db = new TextDatabase("messages.db", "users.db");
        System.out.println(db.register(new User(args[0], args[1], args[2])));
    }
}
