package com.sqlinjectiondemo.utils;

import com.sqlinjectiondemo.exception.PasswordNotValidException;
import com.sqlinjectiondemo.exception.UserNameNotValidException;
import org.springframework.stereotype.Component;

@Component
public class SqlValidator {
    public static final String SUITABLE_CHARACTERS_FOR_USERNAME_REGEX = "^[a-zA-Z0-9]{1,10}$";
    public static final String SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX = "^(?=.*[A-ZA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,10}";

    public void isValidUsernameRegex(String username) {
        if (!username.matches(SUITABLE_CHARACTERS_FOR_USERNAME_REGEX)) {
            throw new UserNameNotValidException("Username is not valid!");
        }
    }

    public void isValidPasswordRegex(String password) {
        if (!password.matches(SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX)) {
            throw new PasswordNotValidException("Password is not valid!");
        }
    }

}
