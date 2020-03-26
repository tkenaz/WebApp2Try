package webapp.command;

import entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/process")
public class LoginSession extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.getAttribute("username");

        String userName = request.getParameter("login_name");
        String password = request.getParameter("password");

        /*if (userName == null || password == null || userName.isEmpty() || password.isEmpty()) { ToDo create own Exeption
            throw new AppExeption ("login and/or password cannot be empty");
        }*/



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
