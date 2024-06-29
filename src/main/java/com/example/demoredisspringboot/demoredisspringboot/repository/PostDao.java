package com.example.demoredisspringboot.demoredisspringboot.repository;

import com.example.demoredisspringboot.demoredisspringboot.entity.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PostDao {
    public static final String HASH_KEY = "Post";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(UUID.randomUUID().toString()); // Genera un UUID solo si el post no tiene uno
            post.setCreatedAt(LocalDateTime.now());
        }
        try {
            String postJson = objectMapper.writeValueAsString(post);
            template.opsForHash().put(HASH_KEY, post.getId(), postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Post object", e);
        }
        return post;
    }

    public List<Post> findAll() {
        List<String> postsJson = template.opsForHash().values(HASH_KEY);
        return postsJson.stream()
                .map(postJson -> {
                    try {
                        return objectMapper.readValue(postJson, Post.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing Post object", e);
                    }
                })
                .collect(Collectors.toList());
    }

    public Post findPostById(String id) {
        String postJson = (String) template.opsForHash().get(HASH_KEY, id);
        try {
            return objectMapper.readValue(postJson, Post.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing Post object", e);
        }
    }

    public String deletePost(String id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Post removed !!";
    }
}