package io.hexlet.service;

import io.hexlet.dto.product.ProductDTO;
import io.hexlet.mapper.ProductMapper;
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
    private ProductMapper productMapper;

    @Autowired
    private UserUtil userUtil;

    public List<ProductDTO> getAllProductsInCart() {
        var user = userUtil.getCurrentUser();
        return productRepository.findAllById(user.getCart())
                .stream()
                .map(productMapper::map)
                .toList();
    }

    public List<ProductDTO> addProductInCart(Long productId) {
        var user = userUtil.getCurrentUser();
        if (productRepository.existsById(productId)) {
            user.addToCart(productId);
            userRepository.save(user);
        }
        return productRepository.findAllById(user.getCart())
                .stream()
                .map(productMapper::map)
                .toList();
    }

    public void deleteProductFromCart(Long productId) {
        var user = userUtil.getCurrentUser();
        user.removeFromCart(productId);
        userRepository.save(user);
    }
}
