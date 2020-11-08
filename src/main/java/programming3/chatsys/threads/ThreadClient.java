package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;

public class ThreadClient extends MessageQueue{
    private ThreadServer server;
    private String userName;
    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public ThreadClient(ThreadServer server) {
        initialize(server);
    }

    public void initialize(ThreadServer server) {
        this.server = server;
        System.out.println("have connected the server.");
    }

    //Send message to the server's Message queue and register itself to server.
    public void initialize(){
        this.server.send(new ChatMessage(userName,"Hello World!"));
        this.server.register(this);

    }


    public void handleMessage(ChatMessage chatMessage) {
        System.out.println("Receive message:"+chatMessage.toString());
    }

    public void shutdown() {
        server.unregister(getName());
        System.out.println("loss connect with server");
    }
}
