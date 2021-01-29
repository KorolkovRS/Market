package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.ProductDTO;
import ru.korolkovrs.market.models.Cart;
import ru.korolkovrs.market.services.CartService;
import ru.korolkovrs.market.services.ProductService;

import java.util.List;

@RestController
@RequestMapping(name = "carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
//    private final ProductService productService;

    @GetMapping
    public List<Cart> getAllCarts() {
        System.out.println("mapw");
        return cartService.getAllCarts();
    }

    @PostMapping
    public Cart saveCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }
}
