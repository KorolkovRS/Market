package ru.korolkovrs.market.models;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class Cart {
    private List<Product> products;
    private Integer TotalSize;
    private Integer TotalPrice;


}
