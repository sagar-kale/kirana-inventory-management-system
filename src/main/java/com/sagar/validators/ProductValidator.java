package com.sagar.validators;

import com.sagar.entity.Product;
import com.sagar.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class ProductValidator implements Validator {
    @Autowired
    private ProductService productService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Product product = (Product) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qty", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purchasePrice", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salePrice", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catId", "NotEmpty");

        if (product.getProductName() != null && productService.isProductExist(product.getProductName())) {
            errors.rejectValue("productName", "product.name.exist");
        }
    }
}