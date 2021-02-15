package ru.korolkovrs.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korolkovrs.market.models.OrderItem;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private String productTitle;
    private Integer quantity;
    private Integer price;
    private Integer pricePerProduct;

    public OrderItemDTO(OrderItem orderItem) {
        this.productTitle = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.productId = orderItem.getProduct().getId();
    }
}
