package programming3.chatsys.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * @author Rico00121
 * refer a  little thinking from code example.
 * All work did by myself.
 */
public class TCPChatClient {
    private String serverHost;
    private int serverPort;
    public BufferedReader reader;
    private BufferedWriter writer;
    private Socket socket;
    public TCPChatClient(String serverHost,int serverPort){
        this.serverHost=serverHost;
        this.serverPort=serverPort;
    }
    protected Socket initServerSocket(String serverHost, int serverPort) throws IOException {
        return new Socket(serverHost, serverPort);
    }
    public void connect() throws IOException {
        this.socket = this.initServerSocket(serverHost, serverPort);
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public void sendMessage(String message) throws IOException {
        System.out.println("Sending message " + message);
        this.writer.write( message + "\r\n");
        this.writer.flush();
    }

    public static void main(String[] args) throws IOException {
        TCPChatClient client=new TCPChatClient("localhost",8080);
        client.connect();
        Thread task=new Thread(() -> {
            try {
                for (String line = client.reader.readLine();line!=null;line=client.reader.readLine()){
                    System.out.println("Receive message from server : "+line);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        });
        task.start();
        System.out.println("Connect to server successfully!");
        while(true){
            Scanner in=new Scanner(System.in);
            String s=in.nextLine();
            if (s.equals("exit")){
                client.socket.close();
                System.out.println("exit successfully.");
                break;
            }
            else {
                client.sendMessage(s);
            }
        }
        return;

    }


}
