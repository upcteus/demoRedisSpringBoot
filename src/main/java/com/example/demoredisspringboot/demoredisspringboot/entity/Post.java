package com.example.demoredisspringboot.demoredisspringboot.entity;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Post")
public class Post implements Serializable {
    @Id
    private String id;

    private String content;

    @Nullable
    private String imageUrl;

    @Nullable
    private String tags;

    @Nullable
    private String userId;
    
    private List<String> likes;
    
    private Boolean isLiked;

    private LocalDateTime createdAt;
}

