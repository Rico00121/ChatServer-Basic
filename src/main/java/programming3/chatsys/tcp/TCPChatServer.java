package programming3.chatsys.tcp;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.SecureTextDatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/**
 * @author Rico00121
 */
public class TCPChatServer {
    private int port;
    private int timeout;
    private boolean isRunning=false;
    private Database database;
    private ServerSocket serverSocket;

    public TCPChatServer(int port, int timeout, Database database) {
        this.port = port;
        this.timeout = timeout;
        this.database = database;
    }
    public void start()  {
        isRunning=true;
        try {
            serverSocket=new ServerSocket(port);
            while (this.isRunning) {
                Socket socket=serverSocket.accept();
                socket.setSoTimeout(timeout);
                System.out.println("Got connection from " + socket);
                Thread task=new Thread(new TCPChatServerSession(socket,database));
                task.start();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void stop() throws IOException {
        isRunning=false;
        serverSocket.close();
    }

    public static void main(String[] args) {
        Database mydatabse=new SecureTextDatabase("messages_test.db","users_test.db");

        TCPChatServer server=new TCPChatServer(8080,500000,mydatabse);
        server.start();
    }
}
