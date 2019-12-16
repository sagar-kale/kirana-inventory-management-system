package com.sagar.controller;

import com.sagar.entity.Customer;
import com.sagar.exceptions.ResourceNotFoundException;
import com.sagar.repository.CustomerRepository;
import com.sagar.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping
    public Page<Customer> getAllCustomer(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @PutMapping("/{customerId}")
    public Customer updatePost(@PathVariable Long customerId, @Valid @RequestBody Customer customerReq) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setName(customerReq.getName());
            customer.setAddress(customerReq.getAddress());
            customer.setPhone(customerReq.getPhone());
            customer.setCreatedAt(customer.getCreatedAt());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("customerId " + customerId + " not found"));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deletePost(@PathVariable Long customerId) {
        return customerRepository.findById(customerId).map(post -> {
            customerRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("customerId " + customerId + " not found"));
    }

    @GetMapping("/exists/{customerId}")
    public boolean isProductNameExists(@PathVariable Long customerId) {
        return customerRepository.existsById(customerId);
    }

    @GetMapping(value = "/{id}")
    public Customer findCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer ID " + id + " not found"));
    }

}
