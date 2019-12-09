package com.sagar.service;

import com.sagar.entity.Category;
import com.sagar.model.Response;
import com.sagar.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSource messageSource;

    private Response<Category> categoryResponse = null;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Response saveCategory(Category category, Locale locale) {
        log.info("adding category :: {}", category);
        Category save = categoryRepository.save(category);
        categoryResponse = new Response<>();
        categoryResponse.setSuccess(true);
        categoryResponse.setEntity(save);
        categoryResponse.setMsg(messageSource.getMessage("cat.save.success", null, locale));
        return categoryResponse;
    }

    public boolean isCategoryNameAlreadyExists(String categoryName) {
        return categoryRepository.existsByCategoryNameIgnoreCase(categoryName);
    }

    public Response deleteCategory(Long id, Locale locale) {
        categoryResponse = new Response<>();
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            categoryResponse.setError(true);
            categoryResponse.setErrMessage(messageSource.getMessage("cat.not.found", null, locale));
            categoryResponse.setStatus(HttpStatus.NOT_FOUND);
            return categoryResponse;
        }
        log.info("Deleting category ::: {}", category);
        categoryRepository.deleteById(id);
        categoryResponse.setSuccess(true);
        categoryResponse.setEntity(category.get());
        categoryResponse.setMsg(messageSource.getMessage("cat.delete.success", null, locale));
        return categoryResponse;
    }
}
