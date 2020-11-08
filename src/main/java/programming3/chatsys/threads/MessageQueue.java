package programming3.chatsys.threads;

import programming3.chatsys.data.ChatMessage;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class MessageQueue implements Runnable{
    private BlockingQueue<ChatMessage> queue;

    public MessageQueue() {
        this.queue = new LinkedBlockingQueue<ChatMessage>();
    }

    public void send(ChatMessage message){
        this.queue.add(message);
    }


    public ChatMessage getMessage(int waitTime) throws InterruptedException {
        while(true){
            if (this.queue.isEmpty()){
                Thread.sleep(waitTime);
            }
            else
                return this.queue.poll();
        }

    }

    public abstract void initialize();



    public abstract void shutdown();

    //Client and Server same operation.
    @Override
    public void run() {
        initialize();
        while(true){
            try {
                ChatMessage chatMessage=getMessage(5000);
                handleMessage(chatMessage);

            } catch (InterruptedException e) {
                shutdown();
                e.printStackTrace();
                break;
            }
        }
    }

    public abstract void handleMessage(ChatMessage message) throws InterruptedException;




}
