package programming3.chatsys;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void parse() {
    }

    @Test
    void format() {
    }

    @Test
    void save() {
        ChatMessage chatMessage=new ChatMessage("rico","nice");
        chatMessage.save("messages_test.txt");
    }
}