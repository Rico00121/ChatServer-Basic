package programming3.chatsys.tcp;

import org.json.JSONObject;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;


import java.io.*;

public class JSONProtocol {
    private Writer writer;
    public JSONProtocol(Writer writer) throws UnsupportedEncodingException {
        this.writer = writer;
    }
    public JSONObject formatUser(String userName,String fullName,String password){
        JSONObject object=new JSONObject();
        object.put("username",userName);
        object.put("fullname",fullName);
        object.put("password",password);
        return object;
    }
    public JSONObject formatUser(User user){
        JSONObject object=new JSONObject();
        object.put("username",user.getUserName());
        object.put("fullname",user.getFullName());
        object.put("password",user.getPassword());
        return object;
    }
    public User parseUser(JSONObject object){
        return new User(object.getString("username"),
                object.getString("fullname"),
                object.getString("password"));
    }
    public void writeUser(User user) throws IOException {
        this.writer.write(formatUser(user).toString()+"\r\n");
        this.writer.flush();
    }
    public JSONObject formatChatMessage(ChatMessage message){
        JSONObject object=new JSONObject();
        object.put("id",message.getId());
        object.put("message",message.getMessage());
        object.put("username",message.getUserName());
        object.put("timestamp",message.getTimestamp().getTime());
        return object;
    }
    public ChatMessage parseChatMessage(JSONObject object){
        return new ChatMessage(object.getInt("id"),
                object.getString("username"),
                object.getLong("timestamp"),
                object.getString("message"));
    }
    public void writeChatMessage(ChatMessage message) throws IOException {
        this.writer.write(formatChatMessage(message).toString()+"\r\n");
        this.writer.flush();
    }
}
