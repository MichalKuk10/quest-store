package controller.levels;

import DAO.LevelDAO;
import DAO.LevelJDBCDAO;
import exception.ReadException;
import model.Level;
import service.LevelService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name ="Level-Edit", urlPatterns = "/levels/edit")
public class LevelUpdateController  extends HttpServlet {
    private LevelDAO levelDAO;
    public int levelId;
    public Level levelToEdit;

    @Override
    public void init() throws ServletException {
        super.init();
        levelDAO = new LevelJDBCDAO();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LevelService levelService = new LevelService();
        boolean validateInput = levelService.mainValidator(request);

        if(validateInput == true) {
            try {
                Level level = levelService.getInformationToUpdate(request, levelToEdit);
                levelDAO.updateLevel(level, levelId);
                response.setHeader("Send", "Success");
                RequestDispatcher dispatcher
                        = request.getRequestDispatcher("/html-cms/levels_update.jsp");
                dispatcher.forward(request, response);
            } catch (ReadException e) {
                request.setAttribute("error_message", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage");
                dispatcher.forward(request, response);
            }
        }
        request.setAttribute("level", levelToEdit);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html-cms/levels_update.jsp");
        dispatcher.forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPut(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String id = parameters.get("id")[0];
        levelId = Integer.parseInt(id);

        try {
            levelToEdit = levelDAO.getLevelToUpdate(levelId);
        } catch (ReadException e) {
            request.setAttribute("error_message", e.getMessage());
        }

        request.setAttribute("level", levelToEdit);
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/html-cms/levels_update.jsp");
            dispatcher.forward(request, response);
}
}
