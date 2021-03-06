package ru.korolkovrs.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korolkovrs.market.models.Product;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private int price;

    public ProductDto(Product p) {
        id = p.getId();
        title = p.getTitle();
        price = p.getPrice();
    }
}
