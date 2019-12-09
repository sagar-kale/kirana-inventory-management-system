package com.sagar.service;

import com.sagar.entity.Category;
import com.sagar.entity.Product;
import com.sagar.model.Response;
import com.sagar.repository.CategoryRepository;
import com.sagar.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageSource messageSource;

    private Response<Product> productResponse = null;

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Page<Product> getAllProductsByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public Response saveProduct(Product product, Locale locale) {
        log.info("adding product :: {}", product);
        productResponse = new Response<>();
        Optional<Category> category = categoryRepository.findById(product.getCatId());
        if (!category.isPresent()) {
            productResponse.setError(true);
            productResponse.setErrMessage(messageSource.getMessage("cat.not.found", null, locale));
            return productResponse;
        }
        product.setCategory(category.get());
        Product save = productRepository.save(product);

        productResponse.setSuccess(true);
        productResponse.setEntity(save);
        productResponse.setMsg(messageSource.getMessage("product.save.success", null, locale));
        return productResponse;
    }

    public boolean isProductExist(String productName) {
        return productRepository.existsByProductNameIgnoreCase(productName);
    }

    public Response deleteProduct(Long id, Locale locale) {
        productResponse = new Response<>();
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            productResponse.setError(true);
            productResponse.setErrMessage(messageSource.getMessage("product.not.found", null, locale));
            productResponse.setStatus(HttpStatus.NOT_FOUND);
            return productResponse;
        }
        log.info("Deleting product ::: {}", product);
        productRepository.delete(product.get());
        productResponse.setSuccess(true);
        productResponse.setEntity(product.get());
        productResponse.setMsg(messageSource.getMessage("product.delete.success", null, locale));
        return productResponse;
    }
}
