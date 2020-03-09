package com.majiang.mgtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.majiang.mgtest.mapper")
public class MgTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgTestApplication.class, args);
    }

}
