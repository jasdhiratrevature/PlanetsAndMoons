package com.revature.repository;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import com.revature.utilities.ConnectionUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UserDaoTest {
    private Connection connection;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = ConnectionUtil.createConnection();
        userDao = new UserDao();
        cleanupUsersTable();
    }

    @DisplayName("RegisterUser::Valid")
    @ParameterizedTest
    @CsvSource({
            "test, pass",
            "test123, pass123",
    })
    @Order(1)
    public void registerUserValid(String uName, String pwd) {
        // Arrange
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername(uName);
        user.setPassword(pwd);

        // Act
        User actualUser = userDao.createUser(user);

        // Assert
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(uName, actualUser.getUsername());
        Assertions.assertEquals(pwd, actualUser.getPassword());
        Assertions.assertTrue(actualUser.getId() > 0);
    }

    @DisplayName("GetUserByUserName::Valid__ExistingUser")
    @Order(2)
    @Test
    public void getUserByUsernameValid() throws SQLException {
        String existingUsername = "existentUser";
        insertTestUser(existingUsername);

        User foundUser = userDao.getUserByUsername(existingUsername);

        // Assert
        Assertions.assertNotNull(foundUser, "User should be found for existing username");
        Assertions.assertEquals(existingUsername, foundUser.getUsername(), "Username should match");
    }

    @DisplayName("GetUserByUserName::Invalid__NonExistingUser")
    @Order(3)
    @Test
    public void getUserByUsernameInvalid() throws SQLException {
        //Arrange
        String nonExistingUsername = "nonExistingUsername";

        User nonExistingUser = userDao.getUserByUsername(nonExistingUsername);

        // Assert
        Assertions.assertNull(nonExistingUser, "User should be null for non-existing username");
    }

    private void insertTestUser(String username) throws SQLException {
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, username);
            statement.setString(2, "password");
            statement.executeUpdate();
        }
    }

    private void cleanupUsersTable() throws SQLException {
        String deleteQuery = "DELETE FROM users";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        }
    }
}
