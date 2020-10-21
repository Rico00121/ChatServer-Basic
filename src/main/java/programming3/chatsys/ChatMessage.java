package programming3.chatsys;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class ChatMessage {
    private int id;
    private String userName;
    private Timestamp time;
    private String message;

    public ChatMessage(String message) {
        this.message = message;
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

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }
    public String format(){
        return null;
    }
    public void parse(String formatted){

    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", time=" + time +
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
                time.equals(that.time) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, time, message);
    }
}
