package programming3.chatsys.data;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a user.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public class User extends TextDatabaseItem {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    private String userName;
    private String fullName;
    private String password;

    private int lastReadId;

    /**
     * Check whether a user name is formatted properly.
     * @param userName user name
     * @return true if the user name only contains alphanumerical characters and underscores.
     */
    public static boolean userNameIsValid(String userName) {
        return USERNAME_PATTERN.matcher(userName).find();
    }

    /**
     * Creates a User from a formatted String.
     * @param formatted A User formatted like this: "<userName>\t<fullname>\t<password>\t<lastReadId>"
     */
    public User(String formatted) {
        super(formatted);
    }

    /**
     * Creates a User
     * @param userName name of the user which can only contains alphanumerical characters and underscores
     * @param fullName full name of the user
     * @param password password of the user
     */
    public User(String userName, String fullName, String password) {
        super();
        this.init(userName, fullName, password, 0);
    }

    private void init(String userName, String fullName, String password, int lastReadId) {
        if (!userNameIsValid(userName)) {
            throw new IllegalArgumentException("userName is invalid");
        }
        if (fullName.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("fullName contains a line feed");
        }
        if (password.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("password contains a line feed");
        }
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.lastReadId = lastReadId;
    }

    /**
     * Updates this User with data from a formatted String.
     *
     * @param formatted A User formatted like this: "<userName>\t<fullname>\t<password>\t<lastReadId>"
     */
    public void parse(String formatted) {
        String[] split = formatted.split("\t", 4);
        if (split.length < 4) {
            throw new IllegalArgumentException("The String does not contain enough tabulations and cannot be parsed");
        } else {
            this.init(split[0], split[1], split[2], Integer.parseInt(split[3]));
        }
    }

    /**
     * Formats this User.
     *
     * @return the formatted User.
     */
    public String format() {
        return this.userName + "\t" + this.fullName + "\t" + this.password + "\t" + this.lastReadId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public int getLastReadId() {
        return lastReadId;
    }

    public void setLastReadId(int lastReadId) {
        this.lastReadId = lastReadId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, fullName, password);
    }
}
