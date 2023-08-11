package com.blog.main.blogApp.repo;

import com.blog.main.blogApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
