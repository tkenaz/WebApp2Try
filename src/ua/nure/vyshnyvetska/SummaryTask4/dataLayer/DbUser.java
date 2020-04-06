package ua.nure.vyshnyvetska.SummaryTask4.dataLayer;

import java.sql.*;

public class DbUser {

    private static Connection connection;

    //static final String jdbcDriver = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/webAppMySQL";
    private static final String USER = "root"; // ToDo admin creds here
    private static final String PASSWORD = "Frfgekmrj391";

//ToDo create a hash pasw

    private static final String GET_CREDS = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String GET_ROLE = "SELECT u.*, r.name FROM users AS u \n" +
            "JOIN roles AS r ON u.role_id = r.id\n" +
            "WHERE u.login = ? \n" +
            "AND u.password = ?; ";

    /*public boolean isValidUserLogin(String login, String password) throws SQLException {

        boolean isValidUser = false;

        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement ps = connection.prepareStatement(GET_ROLE);;

        try {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            String log = rs.getString("user_name");
            String psw = rs.getString("password"); // ToDo use the hash

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            connection.close();
        }

        return isValidUser;
    }*/

    public String getUserRole(String login, String password) throws SQLException {
        String role = null;
        ResultSet rs = null;

        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement ps = connection.prepareStatement(GET_ROLE);

        try {
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString(8);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("DbUser#getUserRole is not working properly");
            e.getMessage();

        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return role;
    }

    public boolean isValidUserCredentials (String login, String password) throws SQLException {
        ResultSet rs = null;
        boolean isValid = false;

        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement ps = connection.prepareStatement(GET_CREDS);

        try {
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                isValid = true;
            } else {
                System.out.println();
                isValid = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("DbUser#isValid is not working properly");
            e.getMessage();

        } finally {
            try {
                if (ps != null) {
                    rs.close();
                    ps.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }

    public static void main(String[] args) throws SQLException {
        DbUser u = new DbUser();
        System.out.println(u.isValidUserCredentials("asmith@gmail.com", "asmith123"));
        System.out.println(u.isValidUserCredentials("wemith@gmail.com", "asmith123"));

        System.out.println(u.getUserRole("asmith@gmail.com", "asmith123"));
        System.out.println(u.getUserRole("mcurie@gmail.com", "shine"));
    }
}
