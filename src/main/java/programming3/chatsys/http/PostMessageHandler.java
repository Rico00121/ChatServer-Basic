package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import programming3.chatsys.data.Database;
import java.io.*;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class PostMessageHandler extends AbstractHandler implements HttpHandler {
    public PostMessageHandler(Database database) {
        super(database);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        printRequest(httpExchange);
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        StringWriter writer=new StringWriter();
        int responseCode=handlePostMessage(httpExchange,reader,writer);
        sendResponse(httpExchange,responseCode,writer.toString());
    }

    /**
     * Handle Post request,take the message of Json format to database.
     * @param httpExchange
     * @param reader
     * @param writer
     * @return status code 400 or 200.
     * @throws IOException
     */
    private int handlePostMessage(HttpExchange httpExchange, Reader reader, Writer writer) throws IOException {
        JSONObject object =readJSON(reader);
        try {
            if (authenticate(httpExchange.getRequestURI())){
                String message=object.optString("message");
                if (message==null){
                    writeError(writer,"null message.");
                    return 400;
                }else {
                    database.addMessage(getUserName(httpExchange),message);
                    return 200;
                }
            }
            else{
                writeError(writer,"Invalid username or password");
                return 400;
            }
        } catch (Exception e) {
            writeError(writer,e.getMessage());
            return 400;
        }
    }


}
