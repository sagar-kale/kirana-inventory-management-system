package com.sagar.service;

import com.sagar.entity.User;
import com.sagar.model.Response;
import com.sagar.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private Response<User> userResponse;

    public Response saveUser(User user) {
        userResponse = new Response<>();
        if (null == user) {
            userResponse.setValidationError(true);
            userResponse.setStatus(HttpStatus.BAD_REQUEST);
            userResponse.setValidationMsg("Please Enter User Data");
            return userResponse;
        }
        User save = userRepository.save(user);
        userResponse.setEntity(user);
        userResponse.setStatus(HttpStatus.OK);
        userResponse.setSuccess(true);
        return userResponse;
    }

    public Response findAllUsers() {
        userResponse = new Response<>();
        userResponse.setStatus(HttpStatus.OK);
        userResponse.setSuccess(true);
        userResponse.setEntities(userRepository.findAll());
        return userResponse;
    }
}
