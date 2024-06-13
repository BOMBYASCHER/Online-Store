package io.hexlet.controller.api;

import io.hexlet.dto.product.ProductShowDTO;
import io.hexlet.exception.ResourceNotFoundException;
import io.hexlet.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    ProductShowDTO show(@PathVariable Long id) throws ResourceNotFoundException {
        return productService.getProduct(id);
    }
}
