package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.beans.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private List<OrderItemDto> items;
    private Integer totalPrice;

    public CartDto(Cart cart) {
        this.items = cart.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.totalPrice = cart.getTotalPrice();
    }
}
