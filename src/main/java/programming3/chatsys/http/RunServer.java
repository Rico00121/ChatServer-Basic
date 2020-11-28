package programming3.chatsys.http;

import programming3.chatsys.data.SQLiteDatabase;

import java.io.IOException;

public class RunServer {
    public static boolean supportsMySQL=false;
    public static boolean supportsTextDB=true;
    public static boolean supportsSQLite=true;
    public static void main(String[] args) throws IOException {
        int port=80;
        String database="test.sqlite";
        HTTPChatServer server=new HTTPChatServer(new SQLiteDatabase(database),port);
        server.start();
    }
}
