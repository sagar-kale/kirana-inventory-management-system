package com.sagar.validators;

import com.sagar.entity.Category;
import com.sagar.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class CategoryValidator implements Validator {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category category = (Category) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "NotEmpty");

        if (category.getCategoryName() != null && categoryService.isCategoryNameAlreadyExists(category.getCategoryName())) {
            errors.rejectValue("categoryName", "cat.name.exist");
        }
    }
}