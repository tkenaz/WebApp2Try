package ua.nure.vyshnyvetska.SummaryTask4.webapp.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    private void log(String message) {
        System.out.println("[ContextListener]" + message);
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(
                    servletContext.getRealPath("WEB-INF/log4j.properties"));
            LOGGER.debug("Log4j has been initialized.");
        } catch(Exception e) {
            log("Cannot configure Log4J.");
            e.printStackTrace();
        }
        log("Log4J initialization finished");
    }

    public void contextDestroyed(ServletContextEvent event) {
        log("Servlet context destruction starts.");
        //no op
        log("Servlet context destruction finished.");
    }

    public void contextInitializer(ServletContextEvent event) {
        log("Servlet context initialization starts.");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();

        log("Servlet context initialization finished.");
    }

    private void initCommandContainer() {
        //initialize command container
        //just load lass to JVM

        try {
            Class.forName("ua/nure/vyshnyvetska/SummaryTask4/webapp/command/CommandContainer");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot initialize Command Container.");
        }
    }
}
