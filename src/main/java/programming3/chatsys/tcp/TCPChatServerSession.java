package programming3.chatsys.tcp;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;
/**
 * @author Rico00121
 */
public class TCPChatServerSession implements Runnable {
    private final Database database;
    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean loginStatus=false;
    private String userName;

    public TCPChatServerSession(Socket socket,Database database) {
        this.database = database;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //Do some initialization
            this.reader=new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
            this.writer=new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
            for (String line=this.reader.readLine();line!=null;line=this.reader.readLine()){
                this.handleMessage(line);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        //After a client input wrong command,close it.
        finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(socket+" loss the connection.");

        }

    }
    //accept command and handle to do operation
    private void handleMessage(String line) throws IOException {
        String[] split=line.split(" ");
        if (split.length>0){
            switch (split[0]){
                case "OK":
                    keepAlive();
                    break;
                case "GET":
                    try {
                        if (split[2].equals("messages")){
                            if (split[1].equals("recent")){
                                sendMessages(handleRecentMessages(Integer.parseInt(split[3])));
                            }
                            else if (split[1].equals("unread")){
                                if(loginStatus){
                                    sendMessages(handleUnreadMessage());
                                }
                                else {
                                    handleUnauthenticated();
                                }
                            }
                            else{
                                handleError(line);
                            }
                        }
                    }catch (ArrayIndexOutOfBoundsException | InterruptedException e){
                        handleUncompletedCommand();
                    }
                    break;
                case "POST":
                    if (loginStatus){
                        handlePostMessage(line);
                    }
                    else{
                        handleUnauthenticated();
                    }
                    break;
                case "REGISTER":
                    try {
                        handleRegister(new User(split[1],split[2],split[3]));
                    }catch (Exception e){
                        handleUncompletedCommand();
                    }
                    break;
                case "LOGIN":
                    try{
                        if (loginStatus){
                            this.writer.write("You have in login status!\r\n");
                            this.writer.flush();
                        }
                        else {
                            handleLogin(split[1],split[2]);
                        }
                    }catch (Exception e){
                        handleUncompletedCommand();
                    }
                    break;
                default:
                    try {
                        handleError(line);
                    }catch (SocketException | InterruptedException e){
                        System.out.println(socket+" close the connection.");
                    }

            }
        }

    }
    //send message to server to keep the connection alive.
    private void keepAlive() throws IOException {
        this.writer.write("Keep connection with server....\r\n");
        this.writer.flush();
    }
    //When server accept a Error message,it will print error message in client and close after 5s.
    private void handleError(String line) throws IOException, InterruptedException {
        this.writer.write("ERROR "+line+"\r\n");
        this.writer.write("After 5 seconds,the window will close...\r\n");
        this.writer.flush();
        Thread.sleep(5000);

        socket.close();
    }
    //if the command is not error just uncompleted,tip it.
    private void handleUncompletedCommand() throws IOException {
        this.writer.write("Please complete the command.\r\n");
        this.writer.flush();
    }
    //input a number n and return n recent messages,if not have n,return max messages.
    private List<ChatMessage> handleRecentMessages(int n){
        List<ChatMessage> messages=database.readMessages();
        n = Math.min(messages.size(), n);
        return messages.subList(messages.size() - n,messages.size());
    }
    //get the user's unread message and return a List of it.
    private List<ChatMessage> handleUnreadMessage(){
        List<ChatMessage> messages=database.getUnreadMessages(userName);
        return messages;
    }
    //send messages to client.
    private void sendMessages(List<ChatMessage> messages) throws IOException {
        System.out.println("Sending " + messages.size() + " to " + socket);
        this.writer.write("MESSAGES "+messages.size()+"\r\n");
        for (ChatMessage message:messages){
            this.sendMessage(message);
        }
        this.writer.flush();

    }
    //be used in Method sendMessages.
    private void sendMessage(ChatMessage message) throws IOException {
        this.writer.write("MESSAGE "+message.getUserName()+" "+message.getTimestamp()+" "+message.getMessage()+"\r\n");
    }
    //POST command.
    private void handlePostMessage(String message) throws IOException {
        System.out.println("Message posted by " + socket + ": " + message);
        database.addMessage(userName,message);
        this.writer.write("OK\r\n");
        this.writer.flush();
    }
    //if the status is unauthenticated,it will give tip.
    private void handleUnauthenticated() throws IOException {
        this.writer.write("ERROR unauthenticated user\r\n");
        this.writer.flush();
    }
    //do register operation
    private void handleRegister(User user) throws IOException {
        if (database.register(user)){
            this.writer.write("OK\r\n");
        }
        else {
            this.writer.write("ERROR username already taken\r\n");
        }
        this.writer.flush();

    }
    //judge whether login.
    private void handleLogin(String userName, String password) throws IOException {
        if (database.authenticate(userName,password)){
            this.writer.write("OK\r\n");
            this.writer.flush();
            loginStatus=true;
            this.userName=userName;
        }
        else{
            handleUnauthenticated();
        }
    }

}
