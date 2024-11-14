package com.basic.basicApp.controller;

import com.basic.basicApp.entity.User;
import com.basic.basicApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllJUser() {
        return userService.getAll();
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.saveNewEntry(user);
    }

    @PutMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewEntry(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}