package com.omtbp.userservice.controller;

import com.omtbp.userservice.dto.AuthRequest;
import com.omtbp.userservice.entity.User;
import com.omtbp.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        user.setIsActive(Boolean.TRUE);
        Boolean status = userService.saveUser(user);
        if(status)
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        else
           return new ResponseEntity<>("Invalid request", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = userService.generateToken(authRequest.getUsername());
            return userService.generateToken(authRequest.getUsername());
        } else {
            return "Invalid credentials";
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return ResponseEntity.ok("Token is valid");
    }
}