package programming3.chatsys.data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 * @version MutexTextDatabase
 * reference and study it
 */
public class SecureTextDatabase extends TextDatabase{
    private final ReentrantLock usersLock = new ReentrantLock();
    private final ReentrantLock messagesLock = new ReentrantLock();
    public SecureTextDatabase(String messageFilename, String userFilename) {
        super(messageFilename, userFilename);
    }
    @Override
    public Map<String, User> readUsers() {
        this.lockUsers();
        try {
            return super.readUsers();
        } finally {
            this.unlockUsers();
        }
    }

    @Override
    public List<ChatMessage> readMessages() {
        this.lockMessages();
        try {
            return super.readMessages();
        } finally {
            this.unlockMessages();
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        this.lockMessages();
        try {
            return super.addMessage(userName, message);
        } finally {
            this.unlockMessages();
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        this.lockUsers();
        try {
            return super.authenticate(userName, password);
        } finally {
            this.unlockUsers();
        }
    }

    @Override
    public boolean register(User user) {
        this.lockUsers();
        try {
            return super.register(user);
        } finally {
            this.unlockUsers();
        }
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        this.lock();
        try {
            return super.getUnreadMessages(userName);
        } finally {
            this.unlock();
        }
    }

    protected void lockUsers() {
        this.usersLock.lock();
    }

    protected void lockMessages() {
        this.messagesLock.lock();
    }

    protected void unlockUsers() {
        this.usersLock.unlock();
    }

    protected void unlockMessages() {
        this.messagesLock.unlock();
    }

    protected void lock() {
        this.lockUsers();
        this.lockMessages();
    }

    protected void unlock() {
        this.unlockUsers();
        this.unlockMessages();
    }

}
