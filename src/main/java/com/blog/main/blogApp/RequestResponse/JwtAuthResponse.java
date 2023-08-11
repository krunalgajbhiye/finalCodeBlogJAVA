package com.blog.main.blogApp.RequestResponse;

import com.blog.main.blogApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

    private  String token;
    private User user;
}
