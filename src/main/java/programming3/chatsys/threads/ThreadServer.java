package programming3.chatsys.threads;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;


import java.util.HashSet;
import java.util.Set;

public class ThreadServer extends MessageQueue {
    public Set<ThreadClient> clients;
    private Database database;

    public ThreadServer(Database database) {
        this.database = database;
        initialize();

    }

    public void initialize() {
        clients=new HashSet<>();
    }

    //make Set of clients accept the message
    private void forward(ChatMessage message) throws InterruptedException {
        for (ThreadClient client:clients) {
            if (client.getName()!=message.getUserName())
                client.send(message);
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
                System.out.println("Client"+client.getName()+"unregister success!");
                clients.remove(client);
            }
        }
    }

    public void shutdown() {
        System.out.println("Server break.");
    }

    public void handleMessage(ChatMessage chatMessage) throws InterruptedException {
        System.out.println("Server received message"+chatMessage);
        chatMessage=this.database.addMessage(chatMessage.getUserName(),chatMessage.getMessage());
        System.out.println("Server saved message"+chatMessage);
        this.forward(chatMessage);
    }
}
