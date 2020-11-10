package programming3.chatsys.threads;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.MutexTextDatabase;
import programming3.chatsys.data.SecureTextDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * @author Rico00121
 */
public class RunChat {
    public static void main(String[] args) throws InterruptedException {
        //database set
        Database database=new SecureTextDatabase("messages_test.db","users_test.db");
        //server
        ThreadServer server=new ThreadServer(database);
        //Thread serverThread=new Thread(server);
        //serverThread.start();
        //client 1
        ThreadClient client1=new ThreadClient(server);
        client1.setUserName("rico1");
        //Thread client1Thread=new Thread(client1);
        //client1Thread.start();
        //client 2
        ThreadClient client2=new ThreadClient(server);
        client2.setUserName("rico2");
        //Thread client2Thread=new Thread(client2);
        //client2Thread.start();
        //AnsweringClient1
        AnsweringThreadClient answeringClient=new AnsweringThreadClient(server);
        answeringClient.setUserName("answerRico1");
        //Thread answeringThread=new Thread(answeringClient);
        //answeringThread.start();

        //executors test
        ExecutorService exec= Executors.newCachedThreadPool();
        exec.submit(server);
        exec.submit(client1);
        exec.submit(client2);
        exec.submit(answeringClient);
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.DAYS);


    }
}
