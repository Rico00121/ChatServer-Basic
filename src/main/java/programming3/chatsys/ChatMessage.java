package programming3.chatsys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class ChatMessage extends TextDatabaseItem {
    private int id;
    private String userName;
    private Timestamp timestamp;
    private String message;
    public ChatMessage(String formatted){
        this.parse(formatted);
    }

    public ChatMessage(int id, String userName, long timestamp, String message) {
        this.init(id, userName, new Timestamp(timestamp), message);
    }
    public ChatMessage(int id, String userName, String message) {
        this.init(id, userName, System.currentTimeMillis(), message);
    }
    public ChatMessage(String userName, String message) {
        this.init(0, userName, System.currentTimeMillis(), message);
    }
    private void init(int id, String userName, Timestamp timestamp, String message) {
        if (!User.userNameIsValid(userName)) {
            throw new IllegalArgumentException("userName is invalid");
        }
        if (message.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("message contains a line feed");
        }
        this.id = id;
        this.userName = userName;
        this.timestamp = timestamp;
        this.message = message;
    }
    private void init(int id, String userName, long timestamp, String message) {
        this.init(id, userName, new Timestamp(timestamp), message);
    }
    public void parse(String formatted) {
        String[] split = formatted.split("\t", 4);
        if (split.length < 4) {
            throw new IllegalArgumentException("The String does not contain enough tabulations and cannot be parsed");
        } else {
            this.init(Integer.parseInt(split[0]), split[1], Long.parseLong(split[2]), split[3]);
        }
    }
    public String format() {
        return this.id + "\t" + this.userName + "\t" + this.timestamp.getTime() + "\t" + this.message;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setTime(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTime() {
        return timestamp;
    }


    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", time=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return id == that.id &&
                userName.equals(that.userName) &&
                timestamp.equals(that.timestamp) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, timestamp, message);
    }
    public void save(File file) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true))) {
            out.write(this.format() + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(file + " cannot be opened", e);
        }
    }
    public void save(String filename) {
        this.save(new File(filename));
    }

}
