package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.User;
import programming3.chatsys.tcp.JSONProtocol;

import java.io.*;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class RegisterUserHandler extends AbstractHandler implements HttpHandler {
    public RegisterUserHandler(Database database) {
        super(database);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        printRequest(httpExchange);
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        StringWriter writer=new StringWriter();
        int responseCode=405;
        if (httpExchange.getRequestMethod().equals("POST")){
            responseCode=handlePostRegister(httpExchange,reader,writer);
        }
        sendResponse(httpExchange,responseCode,writer.toString());
    }
    private int handlePostRegister(HttpExchange httpExchange, Reader reader, Writer writer) throws IOException {
        JSONObject object = readJSON(reader);
        JSONProtocol jsonProtocol=new JSONProtocol(writer);
        try{
            User user=jsonProtocol.parseUser(object);
            if (user.getUserName().equals(getUserName(httpExchange))){
                if (database.register(user)){
                    return 200;
                }
                else {
                    writeError(writer,"Username already exists");
                    return 400;
                }
            }
            else{
                writeError(writer,"The value of the username don't match the one in the URI.");
                return 401;
            }
        } catch (IOException e) {
            writeError(writer,e.getMessage());
            return 405;
        }
    }

    /**
     * Get user name of the uri included for judging the post and this one whether equal.
     * @param httpExchange
     * @return the user name of the uri included.
     */
    @Override
    public String getUserName(HttpExchange httpExchange){
        String root = httpExchange.getHttpContext().getPath();
        String path = httpExchange.getRequestURI().getPath();
        return path.substring(root.length()).split("/", 1)[0];
    }


}
