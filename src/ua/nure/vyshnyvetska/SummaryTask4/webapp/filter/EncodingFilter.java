package ua.nure.vyshnyvetska.SummaryTask4.webapp.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Encoding filter initializaton starts.");
        encoding = filterConfig.getInitParameter("encoding");
        LOGGER.trace("Encoding from web.xml ==> " + encoding);
        LOGGER.debug("Encoding filter initialization finished.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Encoding filter starts.");

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        LOGGER.trace("Request url ==> " + httpServletRequest.getRequestURI());

        String requestEncoding = servletRequest.getCharacterEncoding();
        if (requestEncoding == null) {
            LOGGER.trace("Request encodins == null, set encoding ==> " + encoding);
            servletRequest.setCharacterEncoding(encoding);
        }

        LOGGER.debug("Encoding filter finished.");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("Encoding filter destruction starts.");
        //no op
        LOGGER.debug("Encoding filter destruction completed.");
    }
}
