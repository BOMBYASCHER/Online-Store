package io.hexlet.controller.api;

import io.hexlet.exception.ResourceNotFoundException;
import io.hexlet.model.Product;
import io.hexlet.repository.ProductRepository;
import io.hexlet.repository.UserRepository;
import io.hexlet.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserUtil userUtil;

    @GetMapping
    List<Product> index() {
        var user = userUtil.getCurrentUser();
        return productRepository.findAllById(user.getCart());
    }
    @PutMapping
    List<Product> update(@RequestBody Long productId) throws ResourceNotFoundException {
        var user = userUtil.getCurrentUser();
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found."));
        user.getCart().add(product.getId());
        userRepository.save(user);
        return productRepository.findAllById(user.getCart());
    }
    @DeleteMapping
    void delete(@RequestBody Long productId) {
        var user = userUtil.getCurrentUser();
        user.getCart().removeIf(product -> product.equals(productId));
        userRepository.save(user);
    }
}
