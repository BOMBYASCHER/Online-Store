package io.hexlet.service;

import io.hexlet.exception.ResourceNotFoundException;
import io.hexlet.model.Product;
import io.hexlet.repository.ProductRepository;
import io.hexlet.repository.UserRepository;
import io.hexlet.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserUtil userUtil;

    public List<Product> getAllProductsInCart() {
        var user = userUtil.getCurrentUser();
        return productRepository.findAllById(user.getCart());
    }

    public List<Product> addProductInCart(Long productId)
            throws ResourceNotFoundException {
        var user = userUtil.getCurrentUser();
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found."));
        user.getCart().add(product.getId());
        userRepository.save(user);
        return productRepository.findAllById(user.getCart());
    }

    public void deleteProductFromCart(Long productId) {
        var user = userUtil.getCurrentUser();
        user.getCart().removeIf(product -> product.equals(productId));
        userRepository.save(user);
    }
}
