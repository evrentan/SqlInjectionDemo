package com.sqlinjectiondemo.utils.constant;

public class ConstantQuery {
    public static final String FIND_USER_BY_USERNAME_PASSWORD_SECURE =
            "select id, username, password " +
                    "from user_table u " +
                    "where u.username = ? " +
                    "and u.password = ?";

    public static final String FIND_USER_BY_USERNAME_PASSWORD =
            "select id, username, password " +
                    "from user_table u " +
                    "where u.username = '%s' " +
                    "and u.password = '%s'";


    public static final String FIND_USER_BY_USERNAME =
            "select id, username, password, name, surname " +
                    "from users u " +
                    "where u.username = '%s'";
}
