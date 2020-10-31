package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.TextDatabase;

import java.util.Scanner;

/**
 * Main program for sending messages with authentication. Takes the username and password as command line arguments.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class SendMessagesAuthentication {
    public static void main(String[] args) {
        String userName = args[0];
        String password = args[1];
        Database db = new TextDatabase("messages.db", "users.db");
        if(!db.authenticate(userName, password)) {
            System.out.println("Invalid username or password.");
            System.exit(1);
        }
        System.out.println("Welcome " + userName);
        System.out.println("Please a message to send");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            ChatMessage message = db.addMessage(userName, scanner.nextLine());
            System.out.println(message);
        }
    }
}