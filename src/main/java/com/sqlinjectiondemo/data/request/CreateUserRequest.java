package com.sqlinjectiondemo.data.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String username;
    private String password;
}
