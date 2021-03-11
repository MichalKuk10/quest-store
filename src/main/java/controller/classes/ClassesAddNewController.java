package controller.classes;

import DAO.CodecoolerClassDAO;
import DAO.CodecoolerClassJDBCDAO;
import DAO.DataSourceReader;
import exception.ConnectionException;
import exception.ReadException;
import model.CodecoolerClass;
import org.postgresql.ds.PGSimpleDataSource;
import service.ClassService;
import validation.ValidationHelper;
import validation.ValidationHelperClass;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClassesAddNew", urlPatterns = "/classes/add")
public class ClassesAddNewController extends HttpServlet {
    private PGSimpleDataSource ds;
    private CodecoolerClassDAO dao;
    private ClassService classService;
    private ValidationHelper validationHelper;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ds = DataSourceReader.getDataSource("src/main/resources/database.properties");
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage());
        }
        dao = new CodecoolerClassJDBCDAO(ds);
        classService = new ClassService(dao);
        validationHelper = new ValidationHelperClass();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/html-cms/classes_add_new.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isInputValid = validationHelper.callInputsValidation(request);
        if (isInputValid) {
            try{
                CodecoolerClass codecoolerClass = classService.extractClassFromHTTPRequest(request);
                dao.addCodecoolerClass(codecoolerClass);
                request.setAttribute("message", "Class has been added successfully!");
            } catch (ReadException e) {
                request.setAttribute("error_message", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage");
                dispatcher.forward(request, response);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html-cms/classes_add_new.jsp");
        dispatcher.forward(request, response);
    }
}
