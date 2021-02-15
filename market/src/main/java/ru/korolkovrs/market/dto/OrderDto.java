package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.models.Order;

@Data
public class OrderDto {
    private Long id;
    private String username;
    private Integer totalPrice;
    private String address;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getUser().getUsername();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
    }
}
