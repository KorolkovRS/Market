package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.beans.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDTO {
    private List<OrderItemDTO> items;
    private Integer totalPrice;

    public CartDTO(Cart cart) {
        this.items = cart.getItems().stream().map(OrderItemDTO::new).collect(Collectors.toList());
        this.totalPrice = cart.getTotalPrice();
    }
}
