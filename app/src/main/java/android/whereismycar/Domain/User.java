package android.whereismycar.Domain;

/**
 * Created by dekeeu on 17/12/2017.
 */

public class User {

    private String userID;
    private String userEmail;
    private String userName;

    public User(String userID, String userEmail, String userName) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public User(String userName, String userEmail) {
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
