package com.example.demoredisspringboot.demoredisspringboot;

import com.example.demoredisspringboot.demoredisspringboot.entity.User;
import com.example.demoredisspringboot.demoredisspringboot.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class DemoRedisSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRedisSpringBootApplication.class, args);
    }

}
