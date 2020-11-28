package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.Database;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Rico00121 (837043207@qq.com)
 */
public class UnreadMessagesHandler extends AbstractHandler implements HttpHandler {
    public UnreadMessagesHandler(Database database) {
        super(database);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        printRequest(httpExchange);
        StringWriter writer=new StringWriter();
        int responseCode=handleGetUnreadMessages(httpExchange,writer);
        sendResponse(httpExchange,responseCode,writer.toString());
    }

    /**
     * handle unread messages
     * @param httpExchange
     * @param writer
     * @return
     * @throws IOException
     */
    private int handleGetUnreadMessages(HttpExchange httpExchange, Writer writer) throws IOException {
        try {
            if (authenticate(httpExchange.getRequestURI())){
                String userName=getUserName(httpExchange);
                writeChatMessages(writer,database.getUnreadMessages(userName));
                return 200;
            }
            else{
                writeError(writer,"Invalid username or password");
                return 401;
            }
        } catch (Exception e) {
            writeError(writer, e.getMessage());
        }
        return 405;
    }
}
