package ua.nure.vyshnyvetska.SummaryTask4.webapp.filter;

import ua.nure.vyshnyvetska.SummaryTask4.InAppPaths;
import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Disabled by default. Uncomment Security filter
 * section in web.xml to enable
 **/

public class CommandAccessFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommandAccessFilter.class);

    // commands access
    private Map<Role, List<String>> accessMap = new HashMap<>();
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts.");

        //roles
        accessMap.put(Role.ADMIN, asList(filterConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, asList(filterConfig.getInitParameter("user")));
        LOGGER.trace("Access map ==> " + accessMap);

        //commons
        commons = asList(filterConfig.getInitParameter("common"));
        LOGGER.trace("Common commands ==> " + commons);

        //out of control
        outOfControl = asList(filterConfig.getInitParameter("out-pf-control"));
        LOGGER.trace("Out of control ==> " + outOfControl);

        LOGGER.debug("Filter initialization finished.");
    }

    /**
     * Extracts parameter values from string.
     * @param str
     *          parameter values string
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
    LOGGER.debug("Filter starts");

    if (accessAllowed(servletRequest)) {
        LOGGER.debug("Filter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    } else {
        String errorMessage = "You do not have permission to access the requested resource";
        servletRequest.setAttribute("errorMessage", errorMessage);
        LOGGER.trace("Set the request attribute: errorMessage ==> " + errorMessage);

        servletRequest.getRequestDispatcher(InAppPaths.PAGE_ERROR_PAGE) //ToDo create package as in the task
                .forward(servletRequest, servletResponse);
    }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = "listMenu";
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false); // ToDo WHAT???
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }
        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    @Override
    public void destroy() {
    LOGGER.debug("Filter destruction starts");
    LOGGER.debug("Filter destruction accomplished");
    }
}
