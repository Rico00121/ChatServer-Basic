package programming3.chatsys;

import java.io.File;
import java.util.Objects;
import java.util.regex.Pattern;

public class User extends TextDatabaseItem {
    private String userName;
    private String fullName;
    private String password;
    private int lastReadId;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    public static boolean userNameIsValid(String userName) {
        return USERNAME_PATTERN.matcher(userName).find();
    }
    public User(String formatted) {
        this.parse(formatted);
    }
    public User(String userName, String fullName, String password) {
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
    public String format() {
        return this.userName + "\t" + this.fullName + "\t" + this.password + "\t" + this.lastReadId;
    }
    public void parse(String formatted) {
        String[] split = formatted.split("\t", 4);
        if (split.length < 4) {
            throw new IllegalArgumentException("The String does not contain enough tabulations and cannot be parsed");
        } else {
            this.init(split[0], split[1], split[2], Integer.parseInt(split[3]));
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return lastReadId == user.lastReadId &&
                userName.equals(user.userName) &&
                fullName.equals(user.fullName) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, fullName, password, lastReadId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", lastReadId=" + lastReadId +
                '}';
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLastReadId(int lastReadId) {
        this.lastReadId = lastReadId;
    }

    public int getLastReadId() {
        return lastReadId;
    }




}
