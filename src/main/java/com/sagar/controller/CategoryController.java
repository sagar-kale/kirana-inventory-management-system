package com.sagar.controller;

import com.sagar.entity.Category;
import com.sagar.entity.Product;
import com.sagar.model.Response;
import com.sagar.service.CategoryService;
import com.sagar.service.ProductService;
import com.sagar.utils.Utils;
import com.sagar.validators.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryValidator categoryValidator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}/products")
    public Page<Product> gellAllProductByCat(@PathVariable Long categoryId, Pageable pageable) {
        return productService.getAllProductsByCategoryId(categoryId, pageable);
    }

    @PostMapping
    public ResponseEntity<Response<Category>> addCategory(@RequestBody @Valid Category category, BindingResult bindingResult, Locale locale) {


        Response errResponse = validateCategory(category, bindingResult, locale);
        if (errResponse != null)
            return ResponseEntity.ok(errResponse);
        log.info("validation succeed");
        Response response = categoryService.saveCategory(category, locale);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<Category>> removeCategory(@PathVariable Long id, Locale locale) {


        if (id == null) {
            Response<Category> errResponse = new Response<>();
            errResponse.setError(true);
            errResponse.setErrMessage("id is required ...");
            return ResponseEntity.ok(errResponse);
        }
        log.info("validation succeed");
        Response response = categoryService.deleteCategory(id, locale);
        return ResponseEntity.ok(response);
    }

    private Response validateCategory(Category category, BindingResult bindingResult, Locale locale) {
        log.info("validating category :: {}", category);
        categoryValidator.validate(category, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("validation errors found ! returning error response");
            Response<Category> errResponse = new Response<>();
            errResponse.setValidationError(true);
            Map<String, String> errorMap = Utils.buildErrorMap(bindingResult, locale, messageSource);
            errResponse.setValidationErrors(errorMap);
            return errResponse;
        }
        return null;
    }
}
