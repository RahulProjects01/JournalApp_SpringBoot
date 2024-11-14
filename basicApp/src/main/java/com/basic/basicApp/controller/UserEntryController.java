package com.basic.basicApp.controller;

import com.basic.basicApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {


    @Autowired
    private com.basic.basicApp.service.UserService userEntry;


    @GetMapping("/all")
    public List<User> getAllJUser() {
        return userEntry.getAll();
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userEntry.saveNewEntry(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userEntry.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userEntry.saveNewEntry(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntry.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
