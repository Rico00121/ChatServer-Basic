package programming3.chatsys;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void readMessages() throws IOException {
        Database database=new Database();
        List<ChatMessage> chatMessageList=new ArrayList<ChatMessage>();
        chatMessageList=database.readMessages();
        for (ChatMessage s:chatMessageList
             ) {System.out.println(s.format());
            
        }
    }
}