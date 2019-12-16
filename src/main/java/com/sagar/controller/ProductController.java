package com.sagar.controller;

import com.sagar.entity.Category;
import com.sagar.entity.Measurement;
import com.sagar.entity.Product;
import com.sagar.exceptions.ResourceNotFoundException;
import com.sagar.model.Response;
import com.sagar.repository.MeasurementRepository;
import com.sagar.repository.ProductRepository;
import com.sagar.service.CategoryService;
import com.sagar.service.ProductService;
import com.sagar.utils.Utils;
import com.sagar.validators.ProductValidator;
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

@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.HEAD,
                RequestMethod.PUT,
                RequestMethod.POST})
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product ID " + id + " not found"));
    }

    @GetMapping("/exists")
    public boolean isProductNameExists(@RequestParam("productName") String productName) {
        return productService.isProductExist(productName);
    }

    @PostMapping
    public ResponseEntity<Response<Product>> addProduct(@RequestBody @Valid Product product, BindingResult bindingResult, Locale locale) {
        System.out.println(product);
        Response errResponse = validateProduct(product, bindingResult, locale);
        if (errResponse != null)
            return ResponseEntity.ok(errResponse);
        log.info("validation succeed");
        Response response = productService.saveProduct(product, locale);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updatePost(@PathVariable Long productId, @Valid @RequestBody Product product, BindingResult bindingResult, Locale locale) {
        String pName = product.getProductName();
        product.setProductName("TEST");
        Response response = validateProduct(product, bindingResult, locale);
        product.setProductName(pName);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return productService.getProductById(productId).map(p -> {
            p.setProductName(product.getProductName());
            p.setMeasure(product.getMeasure());
            Category category = categoryService.findCategoryById(product.getCatId()).orElseThrow(() -> new ResourceNotFoundException("Category Id " + product.getCatId() + " not found"));
            p.setCategory(category);
            p.setPurchasePrice(product.getPurchasePrice());
            p.setSalePrice(product.getSalePrice());
            p.setDescription(product.getDescription());
            p.setQty(product.getQty());
            return ResponseEntity.ok(productRepository.save(p));
        }).orElseThrow(() -> new ResourceNotFoundException("Product Id " + productId + " not found"));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Response<Product>> removeProduct(@PathVariable Long id, Locale locale) {
        log.info("Deleting Product ID :: {}", id);
        if (id == null) {
            Response<Product> errResponse = new Response<>();
            errResponse.setValidationError(true);
            errResponse.setErrMessage("id is required ...");
            return ResponseEntity.ok(errResponse);
        }
        log.info("validation succeed");
        Response response = productService.deleteProduct(id, locale);
        return ResponseEntity.ok(response);
    }

    /**
     * Measurement Methods Starts from Here
     **/

    @GetMapping("/measurements")
    public Page<Measurement> getAllPosts(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

    @PostMapping("/measurement")
    public Measurement createMeasurement(@RequestBody @Valid Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @PutMapping("/measurement/{measureId}")
    public Measurement updatePost(@PathVariable Long measureId, @Valid @RequestBody Measurement measurement) {
        return measurementRepository.findById(measureId).map(measure -> {
            measure.setName(measurement.getName());
            return measurementRepository.save(measure);
        }).orElseThrow(() -> new ResourceNotFoundException("Measure Id " + measureId + " not found"));
    }

    @DeleteMapping("/measurement/{measureId}")
    public ResponseEntity<?> deletePost(@PathVariable Long measureId) {
        return measurementRepository.findById(measureId).map(post -> {
            measurementRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Measure Id " + measureId + " not found"));
    }


    private Response validateProduct(Product product, BindingResult bindingResult, Locale locale) {
        log.info("validating product :: {}", product);
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("validation errors found ! returning error response");
            Response<Product> errResponse = new Response<>();
            errResponse.setValidationError(true);
            Map<String, String> errorMap = Utils.buildErrorMap(bindingResult, locale, messageSource);
            errResponse.setValidationErrors(errorMap);
            return errResponse;
        }
        return null;
    }

}
