package ua.nure.vyshnyvetska.SummaryTask4.webapp.command;

import org.apache.log4j.Logger;
import ua.nure.vyshnyvetska.SummaryTask4.InAppPaths;
import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.DBManager;
import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.Role;
import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.entity.User;
import ua.nure.vyshnyvetska.SummaryTask4.exceptions.AppException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        LOGGER.debug("Command starts");
        HttpSession session = request.getSession();

        // obtain login and psw from the request

        DBManager manager = DBManager.getInstance();
        String login = request.getParameter("login");
        LOGGER.trace("Request parameter: login ==> " + login);

        String password = request.getParameter("password");
        if(login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login and/or password cannot be empty");
        }

        User user = manager.findUserByLogin(login);
        LOGGER.trace("Found in DB: user ==> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            throw new AppException("Cannot find user with such login/password");
        }

        Role userRole = Role.getRole(user);
        LOGGER.trace("userRole ==> " + userRole);

        String forward = InAppPaths.PAGE_ERROR_PAGE;

        if (userRole == Role.ADMIN) {
            forward = InAppPaths.COMMAND_LIST_ORDERS;
        }

        if (userRole == Role.USER) {
            forward = InAppPaths.COMMAND_LIST_MENU;
        }

        session.setAttribute("user", user);
        LOGGER.trace("Set current session attribute: user ==> " + user);

        session.setAttribute("userRole", userRole);
        LOGGER.trace("Set current session attribute: userRole ==> " + userRole);

        LOGGER.info("User " + user + " has logged as " + userRole.toString().toLowerCase());
        LOGGER.debug("Command executed");
        return forward;
    }
}
