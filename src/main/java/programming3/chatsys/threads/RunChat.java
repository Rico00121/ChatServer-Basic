package programming3.chatsys.threads;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.MutexTextDatabase;
import programming3.chatsys.data.TextDatabase;

import javax.xml.soap.Text;

public class RunChat {
    public static void main(String[] args) {
        //database set
        Database database=new MutexTextDatabase("messages_test.db","users_test.db");
        //server
        ThreadServer server=new ThreadServer(database);
        Thread serverThread=new Thread(server);
        serverThread.start();
        //client 1
        ThreadClient client1=new ThreadClient(server);
        client1.setName("rico1");
        client1.initialize();
        Thread client1Thread=new Thread(client1);
        client1Thread.start();
        //client 2
        ThreadClient client2=new ThreadClient(server);
        client2.setName("rico2");
        client2.initialize();
        Thread client2Thread=new Thread(client2);
        client2Thread.start();

    }
}
