package com.blog.main.blogApp.controller;

import com.blog.main.blogApp.RequestResponse.JwtAuthRequest;
import com.blog.main.blogApp.RequestResponse.JwtAuthResponse;
import com.blog.main.blogApp.entity.User;
import com.blog.main.blogApp.helper.JwtTokenHelper;
import com.blog.main.blogApp.services.CustomUserDetailService;
import com.blog.main.blogApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.StringDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authenticate")
public class JwtAuthenticationController {

   @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenUHelper;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<User> SaveUser(@RequestBody User user){
        User saveUser = this.userService.Save(user);
        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody JwtAuthRequest authenticationRequest) throws Exception {
        System.out.println("entered in controller " +authenticationRequest.getUserName() );
        this.authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

         UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUserName());

        String token = jwtTokenUHelper.generateToken(userDetails);

        JwtAuthResponse jwtAuthRequest = new JwtAuthResponse(token, (User) userDetails);

        return new ResponseEntity<JwtAuthResponse>(jwtAuthRequest, HttpStatus.OK);
    }

    private void  authenticate(String username, String password) throws Exception {
        try {
            System.out.println("entered in authenticate " + username + " " + password);
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            }
            catch (Exception e){
                System.out.println("Exception in authenticationManager "+ e);
            };
            System.out.println("after entered in authenticate " + username + " " + password);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
