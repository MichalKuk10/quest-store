package controller.levels;


import DAO.LevelDAO;
import DAO.LevelJDBCDAO;
import exception.ReadException;
import model.Level;
import service.LevelService;
//import service.LevelService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet(name = "Levels-add", urlPatterns = "/levels/add")
public class LevelAddNewController extends HttpServlet {
    private LevelDAO levelDAO;


    @Override
    public void init() throws ServletException {
        super.init();
        try{
            levelDAO = new LevelJDBCDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LevelService levelService = new LevelService();
        boolean validateInput = levelService.mainValidator(req);

        if(validateInput == true) {
            try {
                Level level = levelService.getInformationFromServlet(req);
                levelDAO.insertNewLevel(level);
                resp.setHeader("Send", "Success");
                RequestDispatcher dispatcher
                        = req.getRequestDispatcher("/html-cms/levels_add_new.jsp");
                dispatcher.forward(req, resp);
            } catch (ReadException e) {
                req.setAttribute("error_message", e.getMessage());
                RequestDispatcher dispatcher = req.getRequestDispatcher("/errorPage");
                dispatcher.forward(req, resp);
            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/html-cms/levels_add_new.jsp");
        dispatcher.forward(req, resp);


    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        RequestDispatcher dispatcher
                = req.getRequestDispatcher("/html-cms/levels_add_new.jsp");
        dispatcher.forward(req, resp);
    }



    }

