package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;
/**
 * @author Rico00121
 */

public class AnsweringThreadClient extends ThreadClient{

    public AnsweringThreadClient(ThreadServer server) {
        super(server);
    }

    @Override
    public void handleMessage(ChatMessage chatMessage) {
        super.handleMessage(chatMessage);
        if (chatMessage.getMessage()=="Hello World!"){
            this.send(new ChatMessage(getUserName(),"Hello "+ getUserName()+"!"));
        }
    }

}
