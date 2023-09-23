package com.sqlinjectiondemo;

import static com.sqlinjectiondemo.utils.SqlValidator.SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX;
import static org.junit.jupiter.api.Assertions.*;

import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.exception.PasswordNotValidException;
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

        LoginUserRequest request =  new LoginUserRequest("testUserName","testPassword");
        assertThrows(UserNotFoundException.class, () -> userService.login(request));
    }

    @Test
    public void testValidUsername() {
        String validUsername = "Valid123";
        assertDoesNotThrow(() -> isValidUsernameRegex(validUsername));
    }

    @Test
    public void testInvalidUsername() {
        String invalidUsername = "Invalid@Username";
        assertThrows(UserNameNotValidException.class, () -> isValidUsernameRegex(invalidUsername));
    }

    private void isValidUsernameRegex(String username) {
        if (!username.matches(SqlValidator.SUITABLE_CHARACTERS_FOR_USERNAME_REGEX)) {
            throw new UserNameNotValidException("Username is not valid!");
        }
    }

    @Test
    public void testValidPassword() {
        String validPassword = "ValidPassword123";
        assertDoesNotThrow(() -> isValidPasswordRegex(validPassword));
    }

    @Test
    public void testInvalidPassword() {
        String invalidPassword = "Invalid#Password";
        assertThrows(PasswordNotValidException.class, () -> isValidPasswordRegex(invalidPassword));
    }

    private void isValidPasswordRegex(String password) {
        if (!password.matches(SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX)) {
            throw new PasswordNotValidException("Password is not valid!");
        }
    }
}
