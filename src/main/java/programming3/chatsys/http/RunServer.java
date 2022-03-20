package programming3.chatsys.http;

import com.sun.net.httpserver.HttpServer;
import programming3.chatsys.data.SQLiteDatabase;
import programming3.chatsys.data.SecureTextDatabase;

import java.io.IOException;

public class RunServer {
    public static boolean supportsMySQL=false;
    public static boolean supportsTextDB=true;
    public static boolean supportsSQLite=true;
    public static void main(String[] args) throws IOException {
        int port=80;
        String database="chatsys.sqlite";
        HTTPChatServer server=new HTTPChatServer(new SQLiteDatabase(database),port);
        server.start();
    }
    public void runTextDatabase() throws IOException {
        int port=8080;
        String messagesDb="messages.db";
        String usersDb="users.db";
        HTTPChatServer server=new HTTPChatServer(new SecureTextDatabase(messagesDb,usersDb),port);
        server.start();
    }
    public void runSQLiteDatabase() throws IOException {
        int port=80;
        String database="chatsys.sqlite";
        HTTPChatServer server=new HTTPChatServer(new SQLiteDatabase(database),port);
        server.start();
    }
}
