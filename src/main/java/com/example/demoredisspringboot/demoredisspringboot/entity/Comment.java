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
@RedisHash("Comment")
public class Comment implements Serializable {
    @Id
    private String id;

    private Long postId;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;


}
