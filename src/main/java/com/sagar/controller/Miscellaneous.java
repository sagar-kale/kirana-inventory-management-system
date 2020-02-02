package com.sagar.controller;

import com.sagar.repository.CustomerRepository;
import com.sagar.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/misc")
@Slf4j
public class Miscellaneous {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getMiscData() {
        Map map = new LinkedHashMap();
        map.put("customerCount", customerRepository.count());
        BigDecimal sum = new BigDecimal(0);
        final List<BigDecimal> bigDecimalList = productRepository.findAll()
                .stream()
                .map(product -> product.getPurchasePrice().multiply(BigDecimal.valueOf(product.getQty())))
                .collect(Collectors.toList());
        for (BigDecimal b : bigDecimalList) {
            sum = sum.add(b);
        }
        map.put("totalLoss", sum);
        return ResponseEntity.ok(map);
    }
}
