package programming3.chatsys.data;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MutexTextDatabase extends TextDatabase {
    private final ReentrantLock usersLock = new ReentrantLock();
    private final ReentrantLock messagesLock = new ReentrantLock();

    public MutexTextDatabase(String messageFilename, String userFilename) {
        super(messageFilename, userFilename);
    }

    public MutexTextDatabase(File messagesFile, File usersFile) {
        super(messagesFile, usersFile);
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
        System.out.println("Trying to acquire users lock.");
        this.usersLock.lock();
        System.out.println("Users lock acquired.");
    }

    protected void lockMessages() {
        System.out.println("Trying to acquire messages lock.");
        this.messagesLock.lock();
        System.out.println("Messages lock acquired.");
    }

    protected void unlockUsers() {
        System.out.println("Releasing users lock.");
        this.usersLock.unlock();
    }

    protected void unlockMessages() {
        System.out.println("Releasing messages lock.");
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
