package com.example.demoredisspringboot.demoredisspringboot.interfaces;

import com.example.demoredisspringboot.demoredisspringboot.entity.Post;
import com.example.demoredisspringboot.demoredisspringboot.repository.PostDao;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostDao postDao;

    @PostMapping
    public Post save(@RequestBody Post post){
        return postDao.save(post);
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postDao.findAll();
    }

    @GetMapping("/{id}")
    public Post findPost(@PathVariable String id){
        return postDao.findPostById(id);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id){
        return postDao.deletePost(id);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post){
        Post postToUpdate = postDao.findPostById(id);
        postToUpdate.setContent(post.getContent());
        postToUpdate.setImageUrl(post.getImageUrl();
        postToUpdate.setTags(post.getTags());
        postToUpdate.setUserId(post.getUserId());
        postToUpdate.setLikes(post.getLikes());
        postToUpdate.setIsLiked(post.getIsLiked());
        return postDao.save(postToUpdate);
    }
}
