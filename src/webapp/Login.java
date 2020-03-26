package webapp;

import entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/process")
public class Login extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        RequestDispatcher rd;


        try {
            String user_name = request.getParameter("login_name");
            String password = request.getParameter("password");

            request.setAttribute("username", request.getParameter("login_name"));
            request.setAttribute("password", request.getParameter("password"));

            if (new User().isValidUserLogin(user_name, password)) {
                // ToDo store username and role in session

                request.getRequestDispatcher("/welcome.jsp").forward(request, response); //ToDo no forward here
                /*if(User.getRole() == "user") {
                    request.getRequestDispatcher("welcome.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("welcomeAdmin.jsp").forward(request, response);
                }*/
            } else {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
