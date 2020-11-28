package programming3.chatsys.tcp;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.SecureTextDatabase;

public class RunServer {
    public static void main(String[] args) {
        int port=8080;
        int timeout=500000;
        String messagesDb="messages.db";
        String usersDb="users.db";
        Database database=new SecureTextDatabase(messagesDb,usersDb);
        TCPChatServer server=new TCPChatServer(port,timeout,database);
        server.start();
    }
}
