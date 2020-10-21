package programming3.chatsys;

import java.util.Objects;

public class User {
    private String userName;
    private String fullName;
    private String password;
    private int lastReadId;

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
