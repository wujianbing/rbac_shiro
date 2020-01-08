package com.jack.rbac_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("com.jack.rbac_shiro.dao")
@CrossOrigin
public class RbacShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacShiroApplication.class, args);
    }

}
