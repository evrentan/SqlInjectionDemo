package com.sqlinjectiondemo.data.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserResponse {

    private Long id;
    private String username;
    private String password;
}
