package com.sqlinjectiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.sqlinjectiondemo", "com.sqlinjectiondemo.utils.aop"})
@EnableAspectJAutoProxy
public class SqlInjectionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlInjectionDemoApplication.class, args);
    }

}
