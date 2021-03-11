package controller.users;

import DAO.UserDAO;
import DAO.UserJDBCDAO;
import model.CMSUser;
import exception.ReadException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

@WebServlet(name = "UserNewController", urlPatterns = "/user/new")
public class UserNewController extends HttpServlet {
    private UserDAO dao;
    private boolean isAdmin;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new UserJDBCDAO("src/main/resources/database.properties");
        }
        catch(IOException ex){
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("person-name");
        String email = request.getParameter("person-mail");
        String city = request.getParameter("person-city");

        if(name == null || email == null || city == null ){
            response.sendRedirect("/html-cms/users_add_new.jsp");
        }

        String defaultPassword = "admin";
        String defaultPicture = "../assets/img/admins-images/penelope-cruz.svg";
        Date actualDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        CMSUser newAdmin = new CMSUser.
                Builder().
                userName(name).
                userEmail(email).
                userCity(city).
                userPassword(defaultPassword).
                userDate(actualDate).
                userPicture(defaultPicture).
                userRole(isAdmin).
                build();

        try {
            dao.addUser(newAdmin);
        } catch (ReadException ex) {
            throw new ServletException(ex);
        }
        response.sendRedirect("/user-list?type=" + newAdmin.getRole().toLowerCase());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String type = parameters.get("type")[0];

        isAdmin = type.equals("admin");

        request.setAttribute("type", type);

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/html-cms/users_add_new.jsp");
        dispatcher.forward(request, response);
        //response.sendRedirect("/html-cms/users_add_new.jsp");
    }
}
