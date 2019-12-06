package com.sagar.controller;

import com.sagar.entity.User;
import com.sagar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/add")
    public User addUser(@RequestBody User user) {
        System.out.println(user.getFullName());
        if (user == null)
            return user;
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
