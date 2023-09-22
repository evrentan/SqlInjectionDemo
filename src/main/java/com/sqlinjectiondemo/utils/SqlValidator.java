package com.sqlinjectiondemo.utils;

import com.sqlinjectiondemo.exception.PasswordNotValidException;
import com.sqlinjectiondemo.exception.UserNameNotValidException;
import org.springframework.stereotype.Component;

@Component
public class SqlValidator {
    /*
    private static final String[] SQL_INJECTION_CHARACTER_FOR_USERNAME =
            new String[] {"'","\"","--", "&","/","|","\\","and","or","union","create","update","delete"};
    private static final String[] SQL_INJECTION_CHARACTER_FOR_PASSWORD=
            new String[] {"\"","--", "/", "\\","and","or","union","create","update","delete"};
*/
    private static final String SUITABLE_CHARACTERS_FOR_USERNAME_REGEX = "^[a-zA-Z0-9]{1,10}$";
    private static final String SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX = "^(?=.*[A-ZA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,10}";

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
