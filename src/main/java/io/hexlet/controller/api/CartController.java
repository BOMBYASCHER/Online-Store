package io.hexlet.controller.api;

import io.hexlet.exception.ResourceNotFoundException;
import io.hexlet.model.Product;
import io.hexlet.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    List<Product> index() {
        return cartService.getAllProductsInCart();
    }

    @PutMapping
    List<Product> update(@RequestBody Long productId)
            throws ResourceNotFoundException {
        return cartService.addProductInCart(productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    void delete(@RequestBody Long productId) {
        cartService.deleteProductFromCart(productId);
    }
}
