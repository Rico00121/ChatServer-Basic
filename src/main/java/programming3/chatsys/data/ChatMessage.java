package programming3.chatsys.data;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a chat message.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class ChatMessage extends TextDatabaseItem {
    private int id;
    private String userName;
    private Timestamp timestamp;
    private String message;

    /**
     * Creates a ChatMessage from a formatted String.
     * @param formatted A ChatMessage formatted like this: "<id>\t<userName>\t<timestamp>\t<message>"
     * @throws IllegalArgumentException If the String or username are not formatted properly
     * @throws NumberFormatException If the id or timestamp cannot be parsed properly
     */
    public ChatMessage(String formatted) {
        super(formatted);
    }

    /**
     * Creates a ChatMessage.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param timestamp time at which this ChatMessage was sent
     * @param message message of this ChatMessage
     */
    public ChatMessage(int id, String userName, Timestamp timestamp, String message) {
        super();
        this.init(id, userName, timestamp, message);
    }

    /**
     * Creates a ChatMessage.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param timestamp time at which this ChatMessage was sent
     * @param message message of this ChatMessage
     */
    public ChatMessage(int id, String userName, long timestamp, String message) {
        super();
        this.init(id, userName, new Timestamp(timestamp), message);
    }

    /**
     * Creates a ChatMessage using the current time as timestamp.
     *
     * @param id unique id for this ChatMessage
     * @param userName name of the user who sent this ChatMessage
     * @param message message of this ChatMessage
     */
    public ChatMessage(int id, String userName, String message) {
        super();
        this.init(id, userName, System.currentTimeMillis(), message);
    }

    /**
     * Creates a ChatMessage using the current time as timestamp and 0 as id.
     *
     * @param userName name of the user who sent this ChatMessage
     * @param message message of this ChatMessage
     */
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

    /**
     * Updates this ChatMessage with data from a formatted String.
     *
     * @param formatted A ChatMessage formatted like this: "<id>\t<userName>\t<timestamp>\t<message>"
     * @throws IllegalArgumentException If the String or username are not formatted properly
     * @throws NumberFormatException If the id or timestamp cannot be parsed properly
     */
    public void parse(String formatted) {
        String[] split = formatted.split("\t", 4);
        if (split.length < 4) {
            throw new IllegalArgumentException("The String does not contain enough tabulations and cannot be parsed");
        } else {
            this.init(Integer.parseInt(split[0]), split[1], Long.parseLong(split[2]), split[3]);
        }
    }

    /**
     * Formats this ChatMessage.
     *
     * @return the formatted ChatMessage.
     */
    public String format() {
        return this.id + "\t" + this.userName + "\t" + this.timestamp.getTime() + "\t" + this.message;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", userName=" + userName +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, timestamp, message);
    }
}
