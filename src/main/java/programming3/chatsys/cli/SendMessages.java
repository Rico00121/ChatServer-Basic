package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.TextDatabase;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main program for sending messages. Takes the username as command line argument.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class SendMessages {
    public static void main(String[] args) throws IOException {
        String userName = args[0];
        Database db = new TextDatabase("messages.db", "users.db");
        System.out.println("Welcome " + userName);
        System.out.println("Please a message to send");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            ChatMessage message = db.addMessage(userName, scanner.nextLine());
            System.out.println(message);
        }
        System.out.println(db.readMessages());
    }
}
