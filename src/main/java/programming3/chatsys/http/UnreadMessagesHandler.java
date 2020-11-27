package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.Database;

import java.io.IOException;
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

    }
}
