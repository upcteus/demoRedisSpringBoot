package com.example.demoredisspringboot.demoredisspringboot.repository;

import com.example.demoredisspringboot.demoredisspringboot.entity.User;
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
public class UserDao {
    public static final String HASH_KEY = "User";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
            user.setCreatedAt(LocalDateTime.now());// Genera un UUID solo si el user no tiene uno
        }
        try {
            String userJson = objectMapper.writeValueAsString(user);
            template.opsForHash().put(HASH_KEY, user.getId(), userJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing User object", e);
        }
        return user;
    }

    public List<User> findAll() {
        List<String> usersJson = template.opsForHash().values(HASH_KEY);
        return usersJson.stream()
                .map(userJson -> {
                    try {
                        return objectMapper.readValue(userJson, User.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing User object", e);
                    }
                })
                .collect(Collectors.toList());
    }

    public User findUserById(String id) {
        String userJson = (String) template.opsForHash().get(HASH_KEY, id);
        try {
            return objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing User object", e);
        }
    }

    public String deleteUser(String id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "User removed !!";
    }
}