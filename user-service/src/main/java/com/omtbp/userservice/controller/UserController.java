package com.omtbp.userservice.controller;

import com.omtbp.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import com.omtbp.userservice.entity.User;
import com.omtbp.userservice.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long userId) {
        UserDto user = userService.getUserDtoById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtoList = userService.getUsers();
        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long userId, @RequestBody User updatedUser) {

        if (userService.getUserById(userId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Update the fields of the existing user with the new values
        updatedUser.setUserId(userId);

        Boolean status = userService.saveUser(updatedUser);
        if(status == true)
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Invalid request", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<String> activateUser(@RequestParam("id") Long userId,
											 @RequestParam("isActive") Boolean isActive) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the isActive field of the existing user with the new values
        user.setIsActive(isActive);

        Boolean status = userService.saveUser(user);
        if(status == true)
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Invalid request", HttpStatus.NOT_FOUND);
    }
}

