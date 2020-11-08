package programming3.chatsys.tcp;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.TextDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * Reference from example code
 */
public class TCPChatServer {
    private int port;
    private int timeout;
    private boolean isRunning = false;
    private final List<String> messages = Collections.synchronizedList(new LinkedList<>());

    public TCPChatServer(int port, int timeout) {
        this.port = port;
        this.timeout = timeout;
    }

    protected ServerSocket initServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    public void start() {
        try(ServerSocket server = this.initServerSocket(port)) {
            this.isRunning = true;
            while (this.isRunning) {
                acceptClient(server);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClient(ServerSocket server) {
        try {
            Socket socket = server.accept();
            System.out.println("Got connection from " + socket);
            socket.setSoTimeout(timeout);
            Thread task = new Thread((Runnable) new TCPChatServerSession(this,socket));
            task.start();
        } catch(SocketTimeoutException e) {
            System.err.println("Socket timeout");
        } catch (IOException e) {
            System.err.println("Error with socket");
            e.printStackTrace();
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages(int n) {
        n = Math.min(this.messages.size(), n);
        return this.messages.subList(this.messages.size() - n, this.messages.size());
    }

    public static void main(String[] args) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        TCPChatServer server = new TCPChatServer(1042, 42000);
        server.start();
    }

}
