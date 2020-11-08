package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;

public class ThreadClient extends MessageQueue implements Runnable{
    private ThreadServer server;
    private String userName;
    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public ThreadClient(ThreadServer server) {
        this.server = server;

    }
    //Send message to the server's Message queue and register itself to server.
    public void initialize(){
        this.server.send(new ChatMessage(userName,"Hello World!"));
        this.server.register(this);

    }
    public void handleMessage(ChatMessage message){

    }
    public void shutdown(){

    }

    //always get message from Message queue.
    @Override
    public void run() {
        while(true){
            try {
                System.out.println(getMessage(5000).toString());

            } catch (InterruptedException e) {
                server.unregister(getName());
                e.printStackTrace();

            }
        }
    }
}
