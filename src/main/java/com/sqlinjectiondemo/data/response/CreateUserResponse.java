package com.sqlinjectiondemo.data.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResponse {

    private Long id;
    private String username;
    private String password;
}
