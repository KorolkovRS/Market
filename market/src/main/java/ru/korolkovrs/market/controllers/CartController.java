package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolkovrs.market.beans.Cart;
import ru.korolkovrs.market.dto.CartDTO;
import ru.korolkovrs.market.models.OrderItem;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;

    @GetMapping
    public CartDTO getCart() {
        return new CartDTO(cart);
    }

    @GetMapping("/add/{id}")
    public void addProductById(@PathVariable Long id) {
        cart.addProduct(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clearAll();
    }
}
