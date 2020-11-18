package programming3.chatsys.tcp;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class JSONProtocolTest {

    @Test
    void formatUser() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=jsonProtocol.formatUser("Rico","Rico_Tea","123456cs");
        System.out.println(object.toString());
        assertEquals(object.getString("username"),"Rico");
        assertEquals(object.getString("fullname"),"Rico_Tea");
        assertEquals(object.getString("password"),"123456cs");

    }

    @Test
    void testFormatUser() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=jsonProtocol.formatUser(new User("Rico","Rico_Tea","123456cs"));
        System.out.println(object.toString());
        assertEquals(object.getString("username"),"Rico");
        assertEquals(object.getString("fullname"),"Rico_Tea");
        assertEquals(object.getString("password"),"123456cs");

    }

    @Test
    void parseUser() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=jsonProtocol.formatUser(new User("Rico","Rico_Tea","123456cs"));
        assertEquals(new User("Rico","Rico_Tea","123456cs"),
                jsonProtocol.parseUser(object));
    }

    @Test
    void writeUser() {
    }

    @Test
    void formatChatMessage() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=jsonProtocol.formatChatMessage(new ChatMessage(0,"Rico","hi"));
        System.out.println(object.toString());
        assertEquals(object.getInt("id"),0);
        assertEquals(object.getString("username"),"Rico");
        assertEquals(object.getString("message"),"hi");
    }

    @Test
    void parseChatMessage() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=jsonProtocol.formatChatMessage(new ChatMessage(0,"Rico","hi"));
        System.out.println(jsonProtocol.parseChatMessage(object).toString());
    }

    @Test
    void writeChatMessage() {
    }
    @Test
    void unFormat() throws UnsupportedEncodingException {
        StringWriter stringWriter=new StringWriter();
        JSONProtocol jsonProtocol=new JSONProtocol(stringWriter);
        JSONObject object=new JSONObject();
        object.put("username","Rico");
        object.put("fullname","Rico_Tea");
        assertEquals(new User("Rico","Rico_Tea","123456cs"),
                jsonProtocol.parseUser(object));
    }
}