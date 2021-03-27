package ru.korolkovrs.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Order;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String username;
    private Integer totalPrice;
    private String address;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getUser().getUsername();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress().getTitle();
    }
}
