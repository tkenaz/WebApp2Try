package webapp.command;

import exceptions.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 1234567890L;

    /**
     * Execution method of command
     * @return Address to go once the command executed*/

    public abstract String execute (HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException, AppException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}
