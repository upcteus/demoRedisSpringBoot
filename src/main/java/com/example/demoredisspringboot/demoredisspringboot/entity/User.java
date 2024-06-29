package com.example.demoredisspringboot.demoredisspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;


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

    private List<String> followers;

    private List<String> following;

    private Boolean isFollowing;

    private LocalDateTime createdAt;
}

