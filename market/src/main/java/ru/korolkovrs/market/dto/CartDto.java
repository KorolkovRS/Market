package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.models.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private List<CartItemDto> items;
    private Integer totalPrice;

    public CartDto(Cart cart) {
        this.items = cart.getCartItems().stream().map(CartItemDto::new).collect(Collectors.toList());
        this.totalPrice = cart.getPrice();
    }
}
