package com.revature.service;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UserServiceTest {

    @Mock
    UserDao userDao;
    @InjectMocks
    UserService userService;

    @ParameterizedTest
    @DisplayName("Register::Positive-LowerCase")
    @CsvSource({
            "user, pass",
            "user123, pass123"
    })
    @Order(1)
    public void registerPositiveLowerCase(String uName, String pwd) {
        //Arrange
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername(uName);
        user.setPassword(pwd);

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);

        when(userDao.createUser(user)).thenReturn(mockUser);

        //Act
        User actualUser = userService.register(mockUser);

        //Assert
        Mockito.verify(this.userDao, times(1)).createUser(user);
        Assertions.assertEquals(mockUser, actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Positive-UpperCase")
    @CsvSource({
            "USER, pass",
            "USER123, PASS123"
    })
    @Order(2)
    public void registerPositiveUpperCase(String uName, String pwd) {
        //Arrange
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername(uName.toLowerCase());
        user.setPassword(pwd);

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);
        when(userDao.createUser(user)).thenReturn(mockUser);

        //Act
        User actualUser = userService.register(mockUser);

        //Assert
        Mockito.verify(this.userDao, times(1)).createUser(user);
        Assertions.assertEquals(mockUser, actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Positive-TrailingSpace")
    @CsvSource({
            "user  , pass",
            "  user123, Pass123"
    })
    @Order(3)
    public void registerPositiveTrailingSpace(String uName, String pwd) {
        //Arrange
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername(uName.trim());
        user.setPassword(pwd);

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);
        when(userDao.createUser(user)).thenReturn(mockUser);

        //Act
        User actualUser = userService.register(mockUser);

        //Assert
        Mockito.verify(this.userDao, times(1)).createUser(user);
        Assertions.assertEquals(mockUser, actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Negative--EmptyField")
    @CsvSource({
            "'', password",
            "user, '",
            "'',''"
    })
    @Order(4)
    public void registerNegativeEmptyField(String uName, String pwd) {
        //Arrange
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);

        //Act
        User actualUser = userService.register(mockUser);

        // Assert that no user was created (null)
        Assertions.assertNull(actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Negative--DuplicateUsername")
    @CsvSource({
            "user, pass123",
            "user2, pass123"
    })
    @Order(5)
    public void registerNegativeDuplicateUsername(String uName, String pwd) {
        //Arrange
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername(uName);
        existingUser.setPassword(pwd);

        User mockUser = new User();
        mockUser.setId(2);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);

        when(userDao.getUserByUsername(uName)).thenReturn(existingUser);

        // Act
        User actualUser = userService.register(mockUser);

        // Assert that no user was created (null) due to duplicate username
        Assertions.assertNull(actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Negative--FieldsTooLong")
    @CsvSource({
            "thisusernameisover30characterlong, pass123",
            "user, thisusernameisover31characterlong"
    })
    @Order(6)
    public void registerNegativeFieldsTooLong(String uName, String pwd) {
        //Arrange
        User mockUser = new User();
        mockUser.setId(2);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);

        // Act
        User actualUser = userService.register(mockUser);

        // Assert that no user was created (null) due to duplicate username
        Assertions.assertNull(actualUser);
    }

    @ParameterizedTest
    @DisplayName("Register::Negative--SQLInjection")
    @CsvSource({
            "Robert'; DROP TABLE users;--, pass123",
            "user, Robert'; DROP TABLE users;--"
    })
    @Order(7)
    public void registerNegativeSQLInjection(String uName, String pwd) {
        //Arrange
        User mockUser = new User();
        mockUser.setId(2);
        mockUser.setUsername(uName);
        mockUser.setPassword(pwd);

        // Act
        User actualUser = userService.register(mockUser);

        // Assert that no user was created (null) due to duplicate username
        Assertions.assertNull(actualUser);
    }
}
