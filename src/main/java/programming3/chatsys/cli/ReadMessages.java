package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.TextDatabase;

import java.io.IOException;
import java.util.List;

/**
 * Main program for reading messages. Takes an optional number of message to read as command line argument.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class ReadMessages {
    public static void main(String[] args) throws IOException {
        Database db = new TextDatabase("messages.db", "users.db");
        int n = 0;
        if (args.length > 0) {
             n = Integer.parseInt(args[0]);
        }
        List<ChatMessage> messages = db.readMessages();
        if (n > 0) {
            messages = messages.subList(messages.size() - n, messages.size());
        }
        for(ChatMessage message: messages) {
            System.out.println(message);
        }
    }
}
