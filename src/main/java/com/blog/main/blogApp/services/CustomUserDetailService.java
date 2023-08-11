package com.blog.main.blogApp.services;

import com.blog.main.blogApp.entity.User;
import com.blog.main.blogApp.exception.ResourceNotFoundException;
import com.blog.main.blogApp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("before" + username);
       Optional<User> userName =  userRepo.findByEmail(username);
        System.out.println("after" + userName);
        userName.orElseThrow(() -> new ResourceNotFoundException("user Not found", HttpStatus.BAD_REQUEST.toString(),username));
        if (userName!=null) {
            System.out.println("access");
            return userName.map(User::new).get();
        } else {
            System.out.println("Failed");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
