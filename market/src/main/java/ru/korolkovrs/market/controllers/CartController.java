package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolkovrs.market.dto.ProductDTO;
import ru.korolkovrs.market.services.CartService;

import java.util.List;

@RestController
@RequestMapping(name = "api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Page<ProductDTO> getAllProducts() {
        return null;
    }
}
