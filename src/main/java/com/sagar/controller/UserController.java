package com.sagar.controller;

import com.sagar.entity.User;
import com.sagar.model.Response;
import com.sagar.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<Response> addUser(@Valid @RequestBody User user, BindingResult bindingResult, Locale locale) {
        log.info("adding user :: {}", user);
        Response response = userService.saveUser(user, bindingResult, locale);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    public ResponseEntity<Response> findAllUsers() {
        Response allUsers = userService.findAllUsers();
        return new ResponseEntity<>(allUsers, allUsers.getStatus());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        return null;
    }
}
