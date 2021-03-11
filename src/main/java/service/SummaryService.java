package service;

import DAO.*;
import model.SummaryAdmin;
import model.SummaryMentor;
import exception.*;

import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.Properties;

public class SummaryService {
    private UserDAO userDAO;
    private QuestDAO questDAO;
    private ArtifactDAO artifactDAO;

    public SummaryService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.questDAO = new QuestJDBCDAO();
        this.artifactDAO = new ArtifactJDBCDAO();
    }

    // all methods with DB queries will gradually be moved to specific DAOs, when they are made

    private Connection connectToDB() throws IOException, SQLException {
        Properties prop = PropertiesReader.readProperties("src/main/resources/database.properties");
        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.passwd");

        return DriverManager.getConnection(url, user, password);
    }

    public SummaryMentor getSummaryMentor() throws SQLException, IOException, ConnectionException, ReadException {
        Connection connectionToDB = connectToDB();
        Statement statement = connectionToDB.createStatement();

        return new SummaryMentor(
                getCodecoolersCount(statement),
                getClassesCount(statement),
                getTeamsCount(statement),
                getQuestsCount(),
                getArtifactsCount()
        );
    }

    public SummaryAdmin getSummaryAdmin() throws SQLException, IOException, ReadException, ConnectionException {
        Connection connectionToDB = connectToDB();
        Statement statement = connectionToDB.createStatement();

        return new SummaryAdmin(
                getCodecoolersCount(statement),
                getClassesCount(statement),
                getTeamsCount(statement),
                getQuestsCount(),
                getArtifactsCount(),
                getAdminsCount(),
                getMentorsCount(),
                getLevelsCount(statement)
        );
    }

    private int getMentorsCount() throws ReadException {
        return userDAO.getMentorsCount();
    }

    private int getAdminsCount() throws ReadException {
        return userDAO.getAdminsCount();
    }

    private int getArtifactsCount() throws ConnectionException, ReadException {
        return artifactDAO.getArtifactsCount();
    }

    private int getCodecoolersCount(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM codecooler; ");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new SQLException();
    }

    private int getClassesCount(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM class; ");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new SQLException();
    }

    private int getTeamsCount(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM team; ");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new SQLException();
    }

    private int getQuestsCount() throws ConnectionException, ReadException {
        return questDAO.getQuestsCount();
    }

    private int getLevelsCount(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM level; ");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new SQLException();
    }
}

