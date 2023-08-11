package com.blog.main.blogApp.controller;


import com.blog.main.blogApp.entity.Blogs;
import com.blog.main.blogApp.entity.Comment;
import com.blog.main.blogApp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/{blogId}")
    public ResponseEntity<Comment> SaveBlogs(@RequestBody Comment comment, @PathVariable Long blogId ){

        Comment saveBlogs = this.commentService.SaveComments(comment,blogId);
        return new ResponseEntity<>(saveBlogs, HttpStatus.CREATED);
    }
}
