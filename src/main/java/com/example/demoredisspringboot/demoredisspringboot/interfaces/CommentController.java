package com.example.demoredisspringboot.demoredisspringboot.interfaces;

import com.example.demoredisspringboot.demoredisspringboot.entity.Comment;
import com.example.demoredisspringboot.demoredisspringboot.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @PostMapping
    public Comment save(@RequestBody Comment comment){
        return commentDao.save(comment);
    }

    @GetMapping
    public List<Comment> getAllComments(){
        return commentDao.findAll();
    }

    @GetMapping("/{id}")
    public Comment findComment(@PathVariable String id){
        return commentDao.findCommentById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable String id){
        return commentDao.deleteComment(id);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable String id, @RequestBody Comment comment){
        Comment commentToUpdate = commentDao.findCommentById(id);
        commentToUpdate.setPostId(comment.getPostId());
        commentToUpdate.setUserId(comment.getUserId());
        commentToUpdate.setContent(comment.getContent());
        return commentDao.save(commentToUpdate);
    }
}