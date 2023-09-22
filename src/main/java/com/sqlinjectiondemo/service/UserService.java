package com.sqlinjectiondemo.service;

import com.sqlinjectiondemo.data.request.CreateUserRequest;
import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.data.response.CreateUserResponse;
import com.sqlinjectiondemo.data.response.LoginUserResponse;
import com.sqlinjectiondemo.entity.User;
import com.sqlinjectiondemo.exception.UserNotFoundException;
import com.sqlinjectiondemo.repository.UserRepository;
import com.sqlinjectiondemo.utils.SqlValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {


    private static final String FIND_USER_BY_USERNAME_PASSWORD_SECURE =
            "select id, username, password " +
                    "from user_table u " +
                    "where u.username = ? " +
                    "and u.password = ?";

    private static final String FIND_USER_BY_USERNAME_PASSWORD =
            "select id, username, password, name, surname " +
                    "from users u " +
                    "where u.username = '%s' " +
                    "and u.password = '%s'";


    private static final String FIND_USER_BY_USERNAME =
            "select id, username, password, name, surname " +
                    "from users u " +
                    "where u.username = '%s'";

    private final UserRepository userRepository;

    private final SqlValidator sqlValidator;
    @PersistenceContext
    private EntityManager entityManager;

    public UserService(UserRepository userRepository, SqlValidator sqlValidator) {
        this.userRepository = userRepository;
        this.sqlValidator = sqlValidator;
    }


    public LoginUserResponse login(LoginUserRequest request) {
        sqlValidator.isValidUsernameRegex(request.getUsername());
        sqlValidator.isValidPasswordRegex(request.getPassword());

        try {
            String[] params = {request.getUsername(),request.getPassword()};
            Query query = entityManager.createNativeQuery(FIND_USER_BY_USERNAME_PASSWORD_SECURE);

            int paramIndex = 1;
            for (Object param: params){
                query.setParameter(paramIndex++, param);
            }
            //Object result = query.getSingleResult();

            List<Object[]> row = query.getResultList();
            return LoginUserResponse.builder()
                    .id((Long) row.get(0)[0])
                    .username((String) row.get(0)[1])
                    .password((String) row.get(0)[2])
                    .build();
        } catch (Exception e) {
            throw new UserNotFoundException(request.getUsername());
        }
    }

    public CreateUserResponse save(CreateUserRequest saveDto){
        User user = User.builder()
                .username(saveDto.getUsername())
                .password(saveDto.getPassword())
                .build();
        userRepository.save(user);

        return CreateUserResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getId())
                .build();
    }
}
