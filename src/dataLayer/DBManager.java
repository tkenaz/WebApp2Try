package dataLayer;

import exceptions.DBException;
import exceptions.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager {
    private static final Logger LOGGER = Logger.getLogger(DBManager.class);
    private static DBManager instance;
    private DataSource dataSource;

/**SQL queries*/

    private static final String GET_CREDS = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String GET_ROLE = "SELECT u.*, r.name FROM users AS u \n" +
            "JOIN roles AS r ON u.role_id = r.id\n" +
            "WHERE u.login = ? \n" +
            "AND u.password = ?; ";

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env"); // ToDo what the hell is that?
            dataSource = (DataSource) envContext.lookup("jdbc/LocalWebApp");
            LOGGER.trace("DataSource ==> " + dataSource);
        } catch (NamingException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            throw new DBException (Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
        }
    }





}
