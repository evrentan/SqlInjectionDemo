package com.sqlinjectiondemo.data.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserRequest {
    private String username;
    private String password;
}
