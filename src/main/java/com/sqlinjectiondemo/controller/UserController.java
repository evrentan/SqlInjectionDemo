package com.sqlinjectiondemo.controller;

import com.sqlinjectiondemo.data.request.CreateUserRequest;
import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.data.response.CreateUserResponse;
import com.sqlinjectiondemo.data.response.LoginUserResponse;
import com.sqlinjectiondemo.entity.User;
import com.sqlinjectiondemo.service.UserService;
import com.sqlinjectiondemo.data.response.GenericResponse;
import com.sqlinjectiondemo.utils.constant.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public GenericResponse<LoginUserResponse> findByNativeUser(@RequestBody LoginUserRequest request) throws Exception {
        LoginUserResponse response = userService.login(request);
        return new GenericResponse<>(
                HttpStatus.OK,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_LOGIN,
                LocalDateTime.now(),
                response
        );
    }

    @PostMapping("/createUser")
    public GenericResponse<CreateUserResponse> createUser(@RequestBody CreateUserRequest request){
        CreateUserResponse response = userService.createUser(request);
        return new GenericResponse<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessage.SUCCESS_CREATE_USER,
                LocalDateTime.now(),
                response
        );
    }

    @GetMapping("/getUser")
    public User createUser(@RequestBody String username, String password) throws SQLException {
        User user =  userService.findUserByUsernameAndPassword(username,password);
        return user;
    }

}
