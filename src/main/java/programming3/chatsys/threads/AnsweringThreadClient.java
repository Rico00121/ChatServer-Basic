package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;

public class AnsweringThreadClient extends ThreadClient{
    private ThreadServer server;

    public AnsweringThreadClient(ThreadServer server) {
        super(server);
    }

    public void handleMessage(ChatMessage message){

    }
}
