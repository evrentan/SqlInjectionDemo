package com.sqlinjectiondemo;

import static org.junit.jupiter.api.Assertions.*;

import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.exception.UserNameNotValidException;
import com.sqlinjectiondemo.exception.UserNotFoundException;
import com.sqlinjectiondemo.service.UserService;
import com.sqlinjectiondemo.utils.SqlValidator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private EntityManager entityManager;
    
    @Test
    public void testLogin_UserNotFound() {
        Mockito.when(entityManager.createNativeQuery(Mockito.anyString())).thenThrow(new UserNotFoundException("testUser"));

        LoginUserRequest request =  LoginUserRequest.builder()
                .username("testUserName")
                .password("testPassword")
                .build();

        assertThrows(UserNotFoundException.class, () -> userService.login(request));
    }

    @Test
    public void testIsValidUsername_ValidUsername() {
        String validUsername = "validUser123";
        SqlValidator sqlValidator = new SqlValidator();
        boolean result = sqlValidator.isValidUsername(validUsername);
        assertTrue(result);
    }

    @Test
    public void testIsValidUsername_BlacklistedUsername() {
        String blacklistedUsername = "ahmet' OR '1' = '1'";
        SqlValidator sqlValidator = new SqlValidator();
        assertThrows(UserNameNotValidException.class, () -> sqlValidator.isValidUsername(blacklistedUsername));
    }
}
