package DAO;

import exception.ReadException;
import model.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LevelDAOTests {

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    PGSimpleDataSource dataSource;


    @BeforeEach
    public void init(){

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_add_new_level_to_database() throws SQLException, ReadException {
        //given:
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("INSERT INTO level (name, description, price, picture_url)" +
                "VALUES (?, ?, ?, ?);")).thenReturn(preparedStatement);


        //when:
        //LevelJDBCDAO dao = new LevelJDBCDAO(dataSource);
        Level level = new Level("Name", "Description", 11, "picture6");
       // dao.insertNewLevel(level);


        //then:
        verify(preparedStatement, times(3)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement).executeUpdate();

    }
}
