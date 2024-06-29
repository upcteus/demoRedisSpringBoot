package com.example.demoredisspringboot.demoredisspringboot.interfaces;

import com.example.demoredisspringboot.demoredisspringboot.entity.Media;
import com.example.demoredisspringboot.demoredisspringboot.repository.MediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @Autowired
    private MediaDao mediaDao;

    @PostMapping
    public Media save(@RequestBody Media media){
        return mediaDao.save(media);
    }

    @GetMapping
    public List<Media> getAllMedia(){
        return mediaDao.findAll();
    }

    @GetMapping("/{id}")
    public Media findMedia(@PathVariable String id){
        return mediaDao.findMediaById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMedia(@PathVariable String id){
        return mediaDao.deleteMedia(id);
    }

    @PutMapping("/{id}")
    public Media updateMedia(@PathVariable String id, @RequestBody Media media){
        Media mediaToUpdate = mediaDao.findMediaById(id);
        mediaToUpdate.setUrl(media.getUrl());
        mediaToUpdate.setType(media.getType());
        return mediaDao.save(mediaToUpdate);
    }
}