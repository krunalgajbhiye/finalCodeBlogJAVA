package com.blog.main.blogApp.repo;

import com.blog.main.blogApp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
