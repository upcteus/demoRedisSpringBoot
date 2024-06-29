package com.example.demoredisspringboot.demoredisspringboot.repository;

import com.example.demoredisspringboot.demoredisspringboot.entity.Comment;
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
public class CommentDao {
    public static final String HASH_KEY = "Comment";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            comment.setId(UUID.randomUUID().toString()); // Genera un UUID solo si el comment no tiene uno
            comment.setCreatedAt(LocalDateTime.now()); // Establece createdAt a la fecha y hora actuales

        }
        try {
            String commentJson = objectMapper.writeValueAsString(comment);
            template.opsForHash().put(HASH_KEY, comment.getId().toString(), commentJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Comment object", e);
        }
        return comment;
    }

    public List<Comment> findAll() {
        List<String> commentsJson = template.opsForHash().values(HASH_KEY);
        return commentsJson.stream()
                .map(commentJson -> {
                    try {
                        return objectMapper.readValue(commentJson, Comment.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing Comment object", e);
                    }
                })
                .collect(Collectors.toList());
    }

    public Comment findCommentById(String id) {
        String commentJson = (String) template.opsForHash().get(HASH_KEY, id);
        try {
            return objectMapper.readValue(commentJson, Comment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing Comment object", e);
        }
    }

    public String deleteComment(String id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Comment removed !!";
    }
}