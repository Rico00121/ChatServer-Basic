package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;
/**
 * @author Rico00121
 */
public class ThreadClient extends MessageQueue{
    private ThreadServer server;
    private String userName;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public ThreadClient(ThreadServer server) {
        this.server = server;
    }

    //Send message to the server's Message queue and register itself to server.
    public void initialize(){
        System.out.println("<Client>"+getUserName()+" have connected the server.");
        server.register(this);
        server.send(new ChatMessage(getUserName(),"Hello World!"));

    }


    public void handleMessage(ChatMessage chatMessage) {
        System.out.println("<Client>"+getUserName()+" Receive message:"+chatMessage.getMessage()+" "+chatMessage.getTimestamp());
    }

    public void shutdown() {
        server.unregister(getUserName());
        System.out.println("<Client>"+getUserName()+" loss connect with server");
    }
}
