package com.sagar.validators;

import com.sagar.entity.User;
import com.sagar.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (user.getEmail() == null) {
            errors.rejectValue("email", "NotEmpty");
        } else if (user.getEmail().length() < 6 || user.getEmail().length() > 50) {
            errors.rejectValue("email", "Size.user.username");
        }

        if (userService.findByUsername(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.user.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "user.name.NotEmpty");
        /*if (user.getFullName().length() < 3 || user.getFullName().length() > 50) {
            errors.rejectValue("fullName", "Size.user.firstName");
        }*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.NotEmpty");
        /*if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.user.password");
        }
*/
        if (user.getRepeatPassword() != null && !user.getRepeatPassword().equals(user.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "Diff.userForm.passwordConfirm");
        }
    }
}