package com.example.demoredisspringboot.demoredisspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("User")
public class User implements Serializable {

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private String profilePicture;

    private String followers;

    private String following;

    private LocalDateTime createdAt;
}
