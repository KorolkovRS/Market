package ru.korolkovrs.market.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.OrderItem;
import ru.korolkovrs.market.models.Product;
import ru.korolkovrs.market.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
@RequiredArgsConstructor
@Data
public class Cart {
    private final ProductService productService;
    private  List<OrderItem> items;
    private Integer totalPrice;

    @PostConstruct
    private void init() {
        items = new ArrayList<>();
    }

    public void addProduct(Long id) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(id)) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product product = productService.findProductsById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id + " doesn't exist"));
        items.add(new OrderItem(product));
        recalculate();
    }

    public void deleteProduct(Long id) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(id)) {
                item.decrementQuantity();
                if (item.getQuantity() == 0) {
                    items.remove(item);
                }
                recalculate();
                return;
            }
        }
    }

    public void clearAll() {
        items.clear();
        recalculate();
    }

    public void recalculate() {
        totalPrice = 0;
        for (OrderItem item : items) {
            totalPrice += item.getPrice();
        }
    }
}
