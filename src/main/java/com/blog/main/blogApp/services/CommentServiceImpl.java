package com.blog.main.blogApp.services;

import com.blog.main.blogApp.entity.Blogs;
import com.blog.main.blogApp.entity.Comment;
import com.blog.main.blogApp.exception.ResourceNotFoundException;
import com.blog.main.blogApp.repo.BlogRepo;
import com.blog.main.blogApp.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Comment SaveComments(Comment comment, Long blog_id) {

        Blogs Blogs= this.blogRepo.findById(blog_id).orElseThrow(() -> new ResourceNotFoundException("Blogs Not found", HttpStatus.BAD_REQUEST.toString(),blog_id.toString()));
        System.out.println("blog "+ Blogs);
        comment.setBlogs(Blogs);
        System.out.println("comment "+ comment);

        Comment comments = this.commentRepo.save(comment);
        return comments;
    }
}
