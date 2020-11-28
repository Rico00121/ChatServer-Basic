package programming3.chatsys.http;

import com.sun.javafx.collections.MappingChange;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import org.json.JSONTokener;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public abstract class AbstractHandler{
    public Database database;

    public AbstractHandler(Database database) {
        this.database = database;
    }

    /**
     *Read a json object from Reader
     * @param reader
     * @return a JSON Object.
     */
    public JSONObject readJSON(Reader reader){
        JSONTokener tokener=new JSONTokener(reader);
        return new JSONObject(tokener);
    }

    /**
     * Write a json object to writer.
     * @param writer
     * @param object
     * @throws IOException
     */
    public void writeJSON(Writer writer,JSONObject object) throws IOException {
        writer.write(object.toString()+"\r\n");
        writer.flush();;
    }

    /**
     * Input a uri,if query's username or password is wrong it will return false
     * @param uri
     * @return
     */
    public boolean authenticate(URI uri){
        String query=uri.getQuery();
        Map<String,String> userNameAndPassword = new HashMap<>();
        for (String param:query.split("&")) {
            String[] entry=param.split("=",2);
            if (entry.length>1){
                userNameAndPassword.put(entry[0],entry[1]);
            }
            else
                return false;
        }
        return database.authenticate(userNameAndPassword.get("username"),userNameAndPassword.get("password"));
    }

    /**
     * Make the list of ChatMessages write into Writer for sending response.
     * @param writer
     * @param messages
     * @throws IOException
     */
    public void writeChatMessages(Writer writer, List<ChatMessage> messages) throws IOException {
        JSONObject object=new JSONObject();
        object.put("type","messages");
        object.put("messages",messages);
        writeJSON(writer,object);
    };

    /**
     * Make the Error message write into Writer for sending response.
     * @param writer
     * @param errorMessage
     * @throws IOException
     */
    public void writeError(Writer writer,String errorMessage) throws IOException {
        JSONObject object=new JSONObject();
        object.put("type","error");
        object.put("errorMessage",errorMessage);
        writeJSON(writer,object);
    }

    /**
     * Send status code and response to client.
     * @param httpExchange
     * @param code status code,such as 400,405,200 and son on.
     * @param response Response Body message,can use Writer.toString().
     * @throws IOException
     */
    public void sendResponse(HttpExchange httpExchange, int code, String response) throws IOException {
        System.out.println("Send reply code<"+code+"> to "+httpExchange.getRemoteAddress());
        httpExchange.getResponseHeaders().add("Content-Type","text/json");
        httpExchange.sendResponseHeaders(code,response.getBytes("UTF-8").length);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(httpExchange.getResponseBody(),"UTF-8"));
        writer.write(response);
        writer.flush();
        writer.close();
    }

    /**
     * Just for test,clearly print simple request information in Console.
     * @param httpExchange
     * @throws IOException
     */
    public void printRequest(HttpExchange httpExchange) throws IOException {
        System.out.println("Received message query from"+httpExchange.getRemoteAddress());
        System.out.println("Requested URI: " + httpExchange.getRequestURI());
        System.out.println("Client: " + httpExchange.getRemoteAddress());
        System.out.println("Request method: " + httpExchange.getRequestMethod());
        System.out.println("Request headers: " + httpExchange.getRequestHeaders().entrySet());
    }
    /**
     * Get the username of this uri query.
     * @param httpExchange
     * @return query's username
     */
    public String getUserName(HttpExchange httpExchange){
        String query=httpExchange.getRequestURI().getQuery();
        String userName=query.split("&")[0].split("=")[1];
        return userName;
    }

}
