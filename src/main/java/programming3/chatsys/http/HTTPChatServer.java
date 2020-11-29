package programming3.chatsys.http;

import com.sun.net.httpserver.HttpServer;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.SQLiteDatabase;

import java.io.IOException;
import java.net.InetSocketAddress;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class HTTPChatServer {
    public Database database;
    public HttpServer server;
    protected final int port;

    public HTTPChatServer(Database database, int port) throws IOException {
        this.database = database;
        this.port=port;
        server=HttpServer.create(new InetSocketAddress(port),0);
        initContexts();
    }

    /**
     * Create context.
     */
    private void initContexts(){
        server.createContext("/recent/",new RecentMessagesHandler(database));
        server.createContext("/message/",new PostMessageHandler(database));
        server.createContext("/unread/",new UnreadMessagesHandler(database));
        server.createContext("/user/",new RegisterUserHandler(database));
    }
    public void start(){
        server.start();
        System.out.println("Server started");
    }

    /**
     * stop server with no delay.
     */
    public void stop(){
        server.stop(0);
    }

}
