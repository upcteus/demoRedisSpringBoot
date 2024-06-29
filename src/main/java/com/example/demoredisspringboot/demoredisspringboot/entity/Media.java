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
@RedisHash("Media")
public class Media implements Serializable {
    @Id
    private String id;

    private String url;

    private String type;

    private LocalDateTime createdAt;
}
