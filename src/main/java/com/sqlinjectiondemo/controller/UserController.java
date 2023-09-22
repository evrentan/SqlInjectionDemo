package com.sqlinjectiondemo.controller;

import com.sqlinjectiondemo.data.request.CreateUserRequest;
import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.data.response.CreateUserResponse;
import com.sqlinjectiondemo.data.response.LoginUserResponse;
import com.sqlinjectiondemo.service.UserService;
import com.sqlinjectiondemo.utils.GenericResponse;
import com.sqlinjectiondemo.utils.constant.SuccessMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public GenericResponse<LoginUserResponse> findByNativeEmployee(@RequestBody LoginUserRequest request){
        LoginUserResponse response = userService.login(request);
        return new GenericResponse<>(
                HttpStatus.OK,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_LOGIN,
                LocalDateTime.now(),
                response
        );
    }

    @PostMapping("/save")
    public GenericResponse<CreateUserResponse> save(@RequestBody CreateUserRequest request){
        CreateUserResponse response = userService.save(request);
        return new GenericResponse<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_CREATE_USER,
                LocalDateTime.now(),
                response
        );
    }

}
