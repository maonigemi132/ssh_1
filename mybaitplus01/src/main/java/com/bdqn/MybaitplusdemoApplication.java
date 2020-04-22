package com.bdqn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bdqn.dao")
public class MybaitplusdemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(MybaitplusdemoApplication.class, args);
    }

}
