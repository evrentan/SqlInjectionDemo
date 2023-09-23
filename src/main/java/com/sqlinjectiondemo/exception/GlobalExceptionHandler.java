package com.sqlinjectiondemo.exception;

import com.sqlinjectiondemo.interceptor.RequestInterceptor;
import com.sqlinjectiondemo.data.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNameNotValidException.class)
    public ResponseEntity<GenericResponse<?>> handleUsernameNotValidException(UserNameNotValidException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<GenericResponse<?>> handlePasswordNotValidException(PasswordNotValidException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericResponse<?>> handledUserNotFoundException(UserNotFoundException ex){
        String message = ex.getExMessage();
        return getGenericResponse(message, HttpStatus.NOT_FOUND);
    }

    private Map<Object, Object> createErrorMap(){
        HttpServletRequest currentRequest = RequestInterceptor.getCurrentRequest();
        Map<Object, Object> errorMap = new HashMap<>();

        if(Objects.nonNull(currentRequest)){
            errorMap.put("HTTP Method", currentRequest.getMethod());
            errorMap.put("Endpoint", currentRequest.getHttpServletMapping().getPattern());
            errorMap.put("HTTP URI", currentRequest.getRequestURI());
            errorMap.put("HTTP URL", currentRequest.getRequestURL());
        }
        return errorMap;
    }

    private ResponseEntity<GenericResponse<?>> getGenericResponse(String ex, HttpStatus httpStatus){
        Map<Object, Object> errorMap = createErrorMap();
        GenericResponse<?> response =new GenericResponse(
                httpStatus,
                httpStatus.value(),
                ex,
                LocalDateTime.now(),
                errorMap
        );
        return ResponseEntity.status(httpStatus).body(response);
    }
}
