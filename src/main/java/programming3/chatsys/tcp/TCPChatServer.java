package programming3.chatsys.tcp;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.SecureTextDatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/**
 * @author Rico00121
 * refer a  little thinking from code example.
 * All work did by myself.
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
    //For server's start
    public void start()  {
        isRunning=true;
        try {
            serverSocket=new ServerSocket(port);
            while (this.isRunning) {
                //do some initialization
                Socket socket=serverSocket.accept();
                socket.setSoTimeout(timeout);
                //when a socket join in,print message and create a new task to keep server and socket session.
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
    //For server's stop.
    public void stop() throws IOException {
        isRunning=false;
        serverSocket.close();
    }


}
