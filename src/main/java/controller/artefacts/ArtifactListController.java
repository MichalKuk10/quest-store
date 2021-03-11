package controller.artefacts;

import DAO.ArtifactDAO;
import DAO.ArtifactJDBCDAO;
import exception.ReadException;
import model.Artifact;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Artifacts", urlPatterns = "/artifacts")

public class ArtifactListController extends HttpServlet {
    private ArtifactDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new ArtifactJDBCDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Artifact> allArtifacts = dao.getAllArtifacts();
            req.setAttribute("allArtifacts", allArtifacts);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html-cms/artifacts_list.jsp");
            dispatcher.forward(req, resp);
        } catch (ReadException e) {
            req.setAttribute("error_message", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/html-cms/error_page.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
