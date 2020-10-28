package programming3.chatsys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    public boolean authenticate(String userName, String password) {
        return false;
    }


    public List<ChatMessage> getUnreadMessage(String userName) {
        return null;
    }


    public List<ChatMessage> readMessages() throws IOException {
        List<ChatMessage> chatMessageList=new ArrayList<ChatMessage>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("messages_test.txt"), "UTF-8"));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            ChatMessage chatMessage=new ChatMessage(line);
            chatMessageList.add(chatMessage);
        }
        reader.close();
        return chatMessageList;
    }


    public Map<String, User> readUsers() {
        return null;
    }


    public void addMessage(ChatMessage message) {

    }


    public void register(User user) {

    }

}
