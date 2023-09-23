package com.sqlinjectiondemo.utils.aop;

import com.sqlinjectiondemo.exception.PasswordNotValidException;
import com.sqlinjectiondemo.exception.UserNameNotValidException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogExceptionAspect {

    private final Logger logger = LoggerFactory.getLogger(LogExceptionAspect.class);

    private ThreadLocal<String> queryThreadLocal = new ThreadLocal<>();

    public void setQuery(String query) {
        queryThreadLocal.set(query);
    }

    public String getQuery() {
        return queryThreadLocal.get();
    }

    public void clearQuery() {
        queryThreadLocal.remove();
    }

    @AfterThrowing(pointcut = "execution(* com.sqlinjectiondemo.service.UserService.*(..))", throwing = "ex")
    public void logError(Exception ex) {

        if (ex instanceof PasswordNotValidException) {
            logger.info("Danger Password query : {}", getQuery());
        }else if (ex instanceof UserNameNotValidException) {
            logger.info("Danger Username query : {}", getQuery());
        }else {
            logger.info("Danger query : {}", getQuery());
        }
    }
}
