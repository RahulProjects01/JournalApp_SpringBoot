package com.basic.basicApp.service;

import com.basic.basicApp.entity.User;
import com.basic.basicApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(user);
    }

    public void saveUser(User user) {
        userEntryRepository.save(user);
    }

    public List<User> getAll() {
        return userEntryRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userEntryRepository.deleteById(id);
    }

    public void deleteByUserName(String userName) {
        User user = userEntryRepository.findByUserName(userName);
        userEntryRepository.deleteById(user.getId());
    }

    public User findByUserName(String userName) {
        return userEntryRepository.findByUserName(userName);
    }

}
