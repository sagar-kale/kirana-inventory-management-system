package com.sagar.service;

import com.sagar.entity.LoggedUser;
import com.sagar.entity.User;
import com.sagar.model.Response;
import com.sagar.repository.UserRepository;
import com.sagar.utils.Utils;
import com.sagar.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Locale;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MessageSource messageSource;

    private Response userResponse;

    public Response saveUser(User user, BindingResult bindingResult, Locale locale) {
        userResponse = new Response<User>();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {


            userResponse.setValidationError(true);
            userResponse.setValidationErrors(Utils.buildErrorMap(bindingResult, locale, messageSource));
            userResponse.setStatus(HttpStatus.BAD_REQUEST);
            userResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return userResponse;
        }
        User save = userRepository.save(user);
        userResponse.setEntity(save);
        userResponse.setStatus(HttpStatus.OK);
        userResponse.setSuccess(true);
        return userResponse;
    }

    public Response findAllUsers() {
        userResponse = new Response<User>();
        userResponse.setStatus(HttpStatus.OK);
        userResponse.setSuccess(true);
        userResponse.setEntities(userRepository.findAll());
        return userResponse;
    }

    public Response login(User user) {
        userResponse = new Response<LoggedUser>();

        User byEmail = userRepository.findByEmail(user.getEmail());
        if (null == byEmail) {
            userResponse.setStatus(HttpStatus.FORBIDDEN);
            userResponse.setError(true);
            userResponse.setErrMessage("Invalid User Name and Password");
            return userResponse;
        }
        userResponse.setStatus(HttpStatus.OK);
        userResponse.setSuccess(true);
        userResponse.setEntities(userRepository.findAll());
        return userResponse;
    }

    public User findByUsername(String email) {
        if (email == null) return null;
        User byEmail = userRepository.findByEmail(email);
        return byEmail;
    }
}
