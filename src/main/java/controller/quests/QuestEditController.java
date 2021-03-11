package controller.quests;

import DAO.QuestDAO;
import DAO.QuestJDBCDAO;
import model.Quest;
import exception.*;
import service.QuestService;
import validation.ValidationHelper;
import validation.ValidationHelperQuest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestEditController", urlPatterns = "/quests/edit")
public class QuestEditController extends HttpServlet {
    private QuestDAO questDAO;
    private QuestService questService;
    private ValidationHelper validationHelper;
    private Quest quest;
    private Integer id;

    @Override
    public void init() throws ServletException {
        super.init();
        this.questDAO = new QuestJDBCDAO();
        this.questService = new QuestService(questDAO);
        this.validationHelper = new ValidationHelperQuest();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            updateQuestIdFromRequestIfExists(request);
            quest = questDAO.getQuestById(id);
            request.setAttribute("quest", quest);
            RequestDispatcher dispatcher
                    = request.getRequestDispatcher("/html-cms/quests_update.jsp");
            dispatcher.forward(request, response);
        } catch (ReadException e) {
            request.setAttribute("error_message", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPut(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isInputValid = validationHelper.callInputsValidation(request);
        if (isInputValid) {
            try {
                quest = questService.changeQuestDetails(request, quest);
                questDAO.updateQuest(quest.getID(), quest);
                request.setAttribute("message", "Quest successfully modified!");
            } catch (ReadException e) {
                request.setAttribute("error_message", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage");
                dispatcher.forward(request, response);
            }
        }
        doGet(request, response);
    }

    private void updateQuestIdFromRequestIfExists(HttpServletRequest request) {
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            // clause left empty on purpose - if we don't jave "id" parameter, the id field already has a value
        }
    }
}