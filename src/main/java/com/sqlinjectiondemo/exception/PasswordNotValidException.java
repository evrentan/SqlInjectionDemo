package com.sqlinjectiondemo.exception;

import com.sqlinjectiondemo.utils.constant.ErrorMessage;

public class PasswordNotValidException extends RuntimeException{

    private String password;

    public PasswordNotValidException(String password){
        this.password = password;
    }

    public String getExMessage(){
        return this.password + " " + ErrorMessage.PASSWORD_NOT_VALID;
    }

}
