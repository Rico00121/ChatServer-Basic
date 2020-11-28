package programming3.chatsys.http;

import com.sun.jndi.toolkit.url.Uri;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.Database;

import java.io.IOException;

public class RegisterUserHandler extends AbstractHandler implements HttpHandler {
    public RegisterUserHandler(Database database) {
        super(database);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
