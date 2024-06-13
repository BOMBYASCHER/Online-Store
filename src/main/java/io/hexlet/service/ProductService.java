package io.hexlet.service;

import io.hexlet.dto.product.ProductDTO;
import io.hexlet.dto.product.ProductShowDTO;
import io.hexlet.exception.ResourceNotFoundException;
import io.hexlet.mapper.ProductMapper;
import io.hexlet.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::map)
                .toList();
    }

    public ProductShowDTO getProduct(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
        return productMapper.toShow(product);
    }
}
