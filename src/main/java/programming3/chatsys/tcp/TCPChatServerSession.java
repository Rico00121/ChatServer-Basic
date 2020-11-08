package programming3.chatsys.tcp;

import java.io.*;
import java.net.Socket;
import java.util.List;
/**
 * Reference from example code
 */
public class TCPChatServerSession {
    private final TCPChatServer server;
    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public TCPChatServerSession(TCPChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }


    public void run() {
        try {
            this.initInputOutput();
            for (String line = this.reader.readLine(); line != null; line = this.reader.readLine()) {
                this.handleMessage(line);
            }
            System.out.println("Ending connection with " + socket);
        } catch(IOException e) {
            System.err.println(e);
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void handleMessage(String line) throws IOException {
        String[] split = line.split(" ");
        if (split.length > 0) {
            switch(split[0]) {
                case "GET":
                    this.handleGetMessages(split);
                    break;
                case "POST":
                    this.handlePostMessage(line.substring(5));
                    break;
                default:
                    this.handleUnknownMessage(line);
            }
        } else {
            System.out.println("Received empty message from " + socket);
        }
    }

    private void handlePostMessage(String message) {
        System.out.println("Message posted by " + socket + ": " + message);
        this.server.addMessage(message);
    }

    private void handleGetMessages(String[] message) throws IOException {
        if (message.length > 1) {
            try {
                int n = Integer.parseInt(message[1]);
                this.sendMessages(this.server.getMessages(n));
            } catch(NumberFormatException e) {
                this.sendError("Cannot be parsed to an integer: " + message[1]);
            }
        } else {
            this.sendError("No number provided in GET message");
        }
    }

    private void sendMessages(List<String> messages) throws IOException {
        System.out.println("Sending " + messages.size() + " to " + socket);
        this.writer.write("MESSAGES " + messages.size() + "\r\n");
        for (String message: messages) {
            this.sendMessage(message);
        }
        this.writer.flush();
    }

    private void sendMessage(String message) throws IOException {
        this.writer.write("MESSAGE " + message + "\r\n");
    }

    private void handleUnknownMessage(String message) throws IOException {
        System.out.println("Got unknown message from " + socket + ": " + message);
        sendError("Unknown message type: " + message);
    }

    private void sendOk() throws IOException {
        this.writer.write("OK\r");
        this.writer.flush();
    }

    private void sendError(String message) throws IOException {
        this.writer.write("ERROR " + message + "\r\n");
        this.writer.flush();
    }

    private void initInputOutput() throws IOException {
        this.writer = new BufferedWriter(
                new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8")
        );
        this.reader = new BufferedReader(
                new InputStreamReader(this.socket.getInputStream(), "UTF-8")
        );
    }
}
