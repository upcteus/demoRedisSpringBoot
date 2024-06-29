package com.example.demoredisspringboot.demoredisspringboot.repository;

import com.example.demoredisspringboot.demoredisspringboot.entity.Media;
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
public class MediaDao {
    public static final String HASH_KEY = "Media";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public Media save(Media media) {
        if (media.getId() == null) {
            media.setId(UUID.randomUUID().toString());
            media.setCreatedAt(LocalDateTime.now());
        }
        try {
            String mediaJson = objectMapper.writeValueAsString(media);
            template.opsForHash().put(HASH_KEY, media.getId(), mediaJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Media object", e);
        }
        return media;
    }

    public List<Media> findAll() {
        List<String> mediasJson = template.opsForHash().values(HASH_KEY);
        return mediasJson.stream()
                .map(mediaJson -> {
                    try {
                        return objectMapper.readValue(mediaJson, Media.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializing Media object", e);
                    }
                })
                .collect(Collectors.toList());
    }

    public Media findMediaById(String id) {
        String mediaJson = (String) template.opsForHash().get(HASH_KEY, id);
        try {
            return objectMapper.readValue(mediaJson, Media.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing Media object", e);
        }
    }

    public String deleteMedia(String id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Media removed !!";
    }
}