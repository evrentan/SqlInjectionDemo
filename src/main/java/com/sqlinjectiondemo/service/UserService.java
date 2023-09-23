package com.sqlinjectiondemo.service;

import com.sqlinjectiondemo.data.request.CreateUserRequest;
import com.sqlinjectiondemo.data.request.LoginUserRequest;
import com.sqlinjectiondemo.data.response.CreateUserResponse;
import com.sqlinjectiondemo.data.response.LoginUserResponse;
import com.sqlinjectiondemo.entity.User;
import com.sqlinjectiondemo.exception.UserNotFoundException;
import com.sqlinjectiondemo.repository.UserRepository;
import com.sqlinjectiondemo.utils.SqlValidator;
import com.sqlinjectiondemo.utils.aop.LogExceptionAspect;
import com.sqlinjectiondemo.utils.constant.ConstantQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final SqlValidator sqlValidator;

    @PersistenceContext
    private EntityManager entityManager;

    private final LogExceptionAspect handleExceptionAspect;

    public UserService(UserRepository userRepository, SqlValidator sqlValidator, LogExceptionAspect handleExceptionAspect) {
        this.userRepository = userRepository;
        this.sqlValidator = sqlValidator;
        this.handleExceptionAspect = handleExceptionAspect;
    }


    public LoginUserResponse login(LoginUserRequest request) throws Exception{
        String sqlString = generateSqlQueryString(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD_SECURE,request.username(),request.password());
        handleExceptionAspect.setQuery(sqlString);
        try {
            String[] params = {request.username(),request.password()};
            Query query = entityManager.createNativeQuery(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD_SECURE);

            //String queryString = generateAndLogSqlQuery(query,params);

            int paramIndex = 1;
            for (Object param: params){
                query.setParameter(paramIndex++, param);
            }
            List<Object[]> row = query.getResultList();
            return new LoginUserResponse((Long) row.get(0)[0],(String) row.get(0)[1]);
        } catch (Exception e) {
            throw new UserNotFoundException(request.username());
        }
    }
    private String generateSqlQueryString(String query, Object... params) {
        if (params.length == 0) {
            return query;
        }
        String filledQuery = query;
        for (Object param : params) {
            String paramValue = param != null ? param.toString() : "null";
            filledQuery = filledQuery.replaceFirst("\\?", "'" + paramValue + "'");
        }

        return filledQuery;
    }

    //Bu aldığı query ve verilerin Strin şekilde sorguyu döndürüyo
    //ama hata alındığı taktirde çalışmaz
    private String generateAndLogSqlQuery(Query query, Object[] params) {
        try {
            String sqlQuery = query.unwrap(org.hibernate.query.Query.class).getQueryString();
            for (Object param : params) {
                sqlQuery = sqlQuery.replaceFirst("\\?", "'" + param.toString() + "'");
            }
            return sqlQuery;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CreateUserResponse createUser(CreateUserRequest request){
        String sqlString = generateSqlQueryString(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD_SECURE,request.username(),request.password());
        handleExceptionAspect.setQuery(sqlString);
        User user = User.builder()
                .username(request.username())
                .password(request.password())
                .build();
        userRepository.save(user);
        return new CreateUserResponse(user.getId(), user.getUsername());
    }

    public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
        String[] params = {username,password};
        String formattedQuery = String.format(ConstantQuery.FIND_USER_BY_USERNAME_PASSWORD, params);

        Query nativeQuery = entityManager.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        }
        return User.builder()
                .id((Long) result.get(0)[0])
                .username((String) result.get(0)[1])
                .password((String) result.get(0)[2])
                .build();
    }




}
