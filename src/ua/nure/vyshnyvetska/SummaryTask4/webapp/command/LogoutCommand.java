package ua.nure.vyshnyvetska.SummaryTask4.webapp.command;

import org.apache.log4j.Logger;
import ua.nure.vyshnyvetska.SummaryTask4.InAppPaths;
import ua.nure.vyshnyvetska.SummaryTask4.exceptions.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Logout command
 * */

public class LogoutCommand extends Command {

    private static final long serialVersionUID = -23456444789876543L;
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return InAppPaths.PAGE_LOGIN;
    }
}
