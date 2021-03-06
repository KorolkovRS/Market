package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolkovrs.market.beans.Cart;
import ru.korolkovrs.market.dto.CartDto;

@RestController
@RequestMapping("/api/v1/auth/carts")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @GetMapping("/add/{id}")
    public void addProductById(@PathVariable Long id) {
        cart.addProduct(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clearAll();
    }

    @GetMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        cart.deleteProduct(id);
    }
}
