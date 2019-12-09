package com.sagar.controller;

import com.sagar.entity.Measurement;
import com.sagar.entity.Product;
import com.sagar.exceptions.ResourceNotFoundException;
import com.sagar.model.Response;
import com.sagar.repository.MeasurementRepository;
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

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
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
    private MeasurementRepository measurementRepository;

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<Response<Product>> addProduct(@RequestBody @Valid Product product, BindingResult bindingResult, Locale locale) {

        Response errResponse = validateProduct(product, bindingResult, locale);
        if (errResponse != null)
            return ResponseEntity.ok(errResponse);
        log.info("validation succeed");
        Response response = productService.saveProduct(product, locale);
        return ResponseEntity.ok(response);
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
