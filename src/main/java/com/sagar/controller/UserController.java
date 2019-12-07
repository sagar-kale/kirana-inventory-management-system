package com.sagar.controller;

import com.sagar.entity.User;
import com.sagar.model.Response;
import com.sagar.repository.UserRepository;
import com.sagar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<Response> addUser(@RequestBody User user) {
        System.out.println(user.getFullName());
        Response response = userService.saveUser(user);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    public ResponseEntity<Response> findAllUsers() {
        Response allUsers = userService.findAllUsers();
        return new ResponseEntity<>(allUsers, allUsers.getStatus());
    }
}
