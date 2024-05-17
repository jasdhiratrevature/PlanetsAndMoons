package com.revature.units;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import com.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUnitTest {

    @Mock
    UserDao userDao;
    @InjectMocks
    UserService userService;

    @Test
    public void createAccount() {
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername("unittest");
        user.setPassword("pass");
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("unittest");
        mockUser.setPassword("pass");
        when(userDao.createUser(user)).thenReturn(mockUser);
        User actualUser = userService.register(mockUser);
        Mockito.verify(this.userDao, times(1)).createUser(user);
        Assertions.assertEquals(mockUser, actualUser);
    }
}