package ua.nure.vyshnyvetska.SummaryTask4.dataLayer;

import com.zaxxer.hikari.HikariDataSource;
import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.entity.User;
import ua.nure.vyshnyvetska.SummaryTask4.exceptions.DBException;
import ua.nure.vyshnyvetska.SummaryTask4.exceptions.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class);
    private static DBManager instance;
    private static DataSource dataSource;

/**SQL queries*/
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String GET_CREDS = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String GET_ROLE = "SELECT u.*, r.name FROM users AS u \n" +
            "JOIN roles AS r ON u.role_id = r.id\n" +
            "WHERE u.login = ? \n" +
            "AND u.password = ?; ";

    /*public static synchronized HikariConfig getInstance() throws DBException {
        if (instance == null) {
            instance = new HikariConfig();
            instance.setJdbcUrl( "jdbc:mysql://localhost:3306/webAppMySQL" );
            instance.setUsername( "root" );
            instance.setPassword( "Frfgekmrj391" );
            dataSource = new HikariDataSource(instance);
        }
        return instance;
    }*/

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env"); // ToDo what the hell is that?
            dataSource = (HikariDataSource) envContext.lookup("jdbc/LocalWebApp");
            LOGGER.trace("DataSource ==> " + dataSource);
        } catch (NamingException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            throw new DBException (Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
        }
    }

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException, DBException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }
        return connection;
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Cannot rollback the transaction;", e);
            }
        }
    }

    /** Below are 4 methods to close connection, statement, and resultset*/

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, e);
            }
        }
    }

    private void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, e);
            }
        }
    }

    private void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, e);
            }
        }
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet){
        close(connection);
        close(statement);
        close(resultSet);
    }

    /**
     * Returns user with the given login.
     *
     * @param  login User login.
     * @return User entity.
     * @throws DBException
     * */

    public User findUserByLogin(String login) throws DBException {

        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(Fields.ENTITY_ID));
        user.setRoleId(resultSet.getInt(Fields.USER_ROLE_ID));
        user.setLogin(resultSet.getString(Fields.USER_LOGIN));
        user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
        return user;
    }





}
