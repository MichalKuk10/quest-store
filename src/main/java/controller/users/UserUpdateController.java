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
import java.util.Map;

@WebServlet(name = "UserUpdateController", urlPatterns = "/user/edit")
public class UserUpdateController extends HttpServlet {
    private UserDAO dao;
    private CMSUser userToEdit;

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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("person-name");
        String email = request.getParameter("person-mail");
        String city = request.getParameter("person-city");

        if(name == null || email == null || city == null){
                response.sendRedirect("/user-list?type=" + userToEdit.getRole().toLowerCase());
        }

        userToEdit.setName(name);
        userToEdit.setEmail(email);
        userToEdit.setCity(city);

        try {
            dao.editUser(userToEdit.getID(), userToEdit);
        } catch (ReadException ex) {
            throw new ServletException(ex);
        }
        response.sendRedirect("/user-list?type=" + userToEdit.getRole().toLowerCase());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPut(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String id = parameters.get("id")[0];

        int idUser = 0;
        try {
            idUser = Integer.parseInt(id);
        } catch(NumberFormatException ex){
            //throw new ServletException("You put incorrect path to page!");
            response.sendRedirect("/dashboard");
        }

        userToEdit = getUser(idUser);

        String type = userToEdit.getRole().toLowerCase();

        request.setAttribute("editAdmin", userToEdit);
        request.setAttribute("type", type);

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/html-cms/users_edit.jsp");
        dispatcher.forward(request, response);
    }

    private CMSUser getUser(int idUser) throws ServletException {
        try {
            return dao.getCMSUser(idUser);
        } catch( ReadException ex) {
            throw new ServletException("You cannot edit user!" + ex);
        }
    }
}
