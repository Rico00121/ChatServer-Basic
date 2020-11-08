package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue{
    private BlockingQueue<ChatMessage> queue;

    public MessageQueue() {
        this.queue = new LinkedBlockingQueue<ChatMessage>();
    }

    public void send(ChatMessage message){
        this.queue.add(message);
    }


    public ChatMessage getMessage(int waitTime) throws InterruptedException {
        if (this.queue.isEmpty()){
            Thread.sleep(waitTime);
            return getMessage(waitTime);
        }
        else
            return this.queue.poll();
    }

    public void initialize(){


    }
    public void handleMessage(ChatMessage message){

    }

    public void shutdown(){

    }


}
