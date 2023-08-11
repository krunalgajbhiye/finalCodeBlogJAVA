package com.blog.main.blogApp.services;

import com.blog.main.blogApp.entity.Comment;

public interface CommentService {
    public Comment SaveComments(Comment comment, Long blog_id);

}
