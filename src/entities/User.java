package entities;
import dataLayer.DbUser;

import java.sql.SQLException;

public class User {
    private String userName;
    private String password;
    private static String role;

    public User() {}

    public User(String userName, String password) { // attributes name, psw. Get the role from the databese
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isValidUserLogin (String userName, String password) throws SQLException {
        DbUser userObject = new DbUser();

        return userObject.isValidUserCredentials(userName, password);
    }

    public static void main(String[] args) throws SQLException {
        User userObject = new User();
        System.out.println(userObject.isValidUserLogin("asmith@gmail.com", "asmith123"));
        System.out.println(userObject.isValidUserLogin("mcurie@gmail.com", "shine"));
        System.out.println(userObject.isValidUserLogin("userA@gmail.com", "Aqwerty"));


    }
}
