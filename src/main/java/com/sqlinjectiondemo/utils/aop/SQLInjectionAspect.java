package com.sqlinjectiondemo.utils.aop;

import com.sqlinjectiondemo.data.request.CreateUserRequest;
import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.exception.PasswordNotValidException;
import com.sqlinjectiondemo.exception.UserNameNotValidException;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SQLInjectionAspect {


    private static final String SUITABLE_CHARACTERS_FOR_USERNAME_REGEX = "^[a-zA-Z0-9]{1,10}$";
    private static final String SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX = "^(?=.*[A-ZA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,10}";


    @After("execution(* com.sqlinjectiondemo.service.UserService.createUser(..)) && args(request)")
    public void beforeLogin(CreateUserRequest request) {
            if (!isValidUsernameRegex(request.username())) {
                throw new UserNameNotValidException("Username is not valid!");
            }if(!isValidPasswordRegex(request.password())){
                throw new PasswordNotValidException("Password is not valid!");
            }
    }
    @After("execution(* com.sqlinjectiondemo.service.UserService.login(..)) && args(request)")
    public void beforeLogin(LoginUserRequest request) {
        if (!isValidUsernameRegex(request.username())) {
            throw new UserNameNotValidException("Username is not valid!");
        }if(!isValidPasswordRegex(request.password())){
            throw new PasswordNotValidException("Password is not valid!");
        }
    }

    private boolean isValidUsernameRegex(String username) {
        return username.matches(SUITABLE_CHARACTERS_FOR_USERNAME_REGEX);
    }

    private boolean isValidPasswordRegex(String username) {
        return username.matches(SUITABLE_CHARACTERS_FOR_PASSWORD_REGEX);
    }


}
