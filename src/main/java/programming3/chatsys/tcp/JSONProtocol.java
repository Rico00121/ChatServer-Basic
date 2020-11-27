package programming3.chatsys.tcp;

import org.json.JSONObject;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;
import java.io.*;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class JSONProtocol {
    private Writer writer;
    public JSONProtocol(Writer writer) throws UnsupportedEncodingException {
        this.writer = writer;
    }

    /**
     * @param userName
     * @param fullName
     * @param password
     * @return
     */
    public JSONObject formatUser(String userName,String fullName,String password){
        JSONObject object=new JSONObject();
        object.put("username",userName);
        object.put("fullname",fullName);
        object.put("password",password);
        return object;
    }

    /**
     * @param user
     * @return
     */
    public JSONObject formatUser(User user){
        JSONObject object=new JSONObject();
        object.put("username",user.getUserName());
        object.put("fullname",user.getFullName());
        object.put("password",user.getPassword());
        return object;
    }

    /**
     * @param object
     * @return
     */
    public User parseUser(JSONObject object){
        return new User(object.getString("username"),
                object.getString("fullname"),
                object.getString("password"));
    }

    /**
     * @param user
     * @throws IOException
     */
    public void writeUser(User user) throws IOException {
        this.writer.write(formatUser(user).toString()+"\r\n");
        this.writer.flush();
    }

    /**
     * @param message
     * @return
     */
    public JSONObject formatChatMessage(ChatMessage message){
        JSONObject object=new JSONObject();
        object.put("id",message.getId());
        object.put("message",message.getMessage());
        object.put("username",message.getUserName());
        object.put("timestamp",message.getTimestamp().getTime());
        return object;
    }

    /**
     * @param object
     * @return
     */
    public ChatMessage parseChatMessage(JSONObject object){
        return new ChatMessage(object.getInt("id"),
                object.getString("username"),
                object.getLong("timestamp"),
                object.getString("message"));
    }

    /**
     * @param message
     * @throws IOException
     */
    public void writeChatMessage(ChatMessage message) throws IOException {
        this.writer.write(formatChatMessage(message).toString()+"\r\n");
        this.writer.flush();
    }
}
