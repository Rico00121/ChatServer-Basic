package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.Database;
import java.io.*;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class RecentMessagesHandler extends AbstractHandler implements HttpHandler {

    public RecentMessagesHandler(Database database) {
        super(database);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        printRequest(httpExchange);
        StringWriter writer=new StringWriter();
        int responseCode=handleGetMessages(httpExchange,writer);
        sendResponse(httpExchange,responseCode,writer.toString());
    }


    /**
     * @param httpExchange
     * @return the n of uri"/recent/< n >"
     */
    private int getN(HttpExchange httpExchange){
        String root = httpExchange.getHttpContext().getPath();
        String path = httpExchange.getRequestURI().getPath();
        return Integer.parseInt(path.substring(root.length()).split("/", 2)[1]);
    }

    /**
     * Handle GET request,get messages from database.
     * @param httpExchange
     * @param writer
     * @return status code 400 or 200
     * @throws IOException
     */
    private int handleGetMessages(HttpExchange httpExchange,Writer writer) throws IOException {
        int n=0;
        try{
            n=getN(httpExchange);
        } catch (Exception e) {
            System.out.println("No such number n");
            writeError(writer,"No such number n");
            return 400;
        }
        writeChatMessages(writer,database.readMessages(n));
        return 200;
    }

}
