package io.hexlet.service;

import io.hexlet.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductServiceInterface {
    Flux<Product> getAllProducts();
    Mono<Product> getProductById(Long id);
}
