package programming3.chatsys.threads;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;


import java.util.HashSet;
import java.util.Set;

public class ThreadServer extends MessageQueue implements Runnable{
    public Set<ThreadClient> clients;
    private Database database;

    public ThreadServer(Database database) {
        this.database = database;
        initialize();
    }
    //make Set of clients accept the message
    private void forward() throws InterruptedException {
        for (ThreadClient client:clients) {
            client.send(getMessage(5000));
        }
    }
    //Add Client to Set of Clients
    public void register(ThreadClient client){
        this.clients.add(client);
        System.out.println("Client register success!");
    }
    //Delete Client from Set of Clients
    public void unregister(String clientName){
        for (ThreadClient client:clients) {
            if (client.getName()==clientName){
                clients.remove(client);
                System.out.println("Client unregister success!");
            }
        }
    }
    @Override
    public void initialize(){
        clients=new HashSet<>();
    }



    public void handleMessage(ChatMessage message){

    }
    public void shutdown(){

    }
    //always get message from Message queue and then add it to database.
    @Override
    public void run() {
        while (true){
            try {
                ChatMessage chatMessage=getMessage(5000);
                database.addMessage(chatMessage.getUserName(),chatMessage.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
