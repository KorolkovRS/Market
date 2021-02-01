package ru.korolkovrs.market.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.OrderItem;
import ru.korolkovrs.market.repositories.ProductRepository;
import ru.korolkovrs.market.services.OrderItemService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class Cart {
    private final ProductRepository productRepository;
    @Autowired
    private OrderItemService orderItemService;
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
        OrderItem orderItem = orderItemService.saveOrUpdateOrderItem(new OrderItem(productRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " doesn't exist"))));
//        OrderItem orderItem = new OrderItem(productRepository.findById(id).get());
        items.add(orderItem);
        recalculate();
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
