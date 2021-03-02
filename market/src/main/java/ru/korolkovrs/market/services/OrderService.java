package ru.korolkovrs.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.korolkovrs.market.beans.Cart;
import ru.korolkovrs.market.dto.OrderDto;
import ru.korolkovrs.market.models.Order;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    public Order saveOrder(User user, String address) {
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Empty cart");
        }
        Order order = new Order(cart, user, address);
        log.info(user.getUsername());
        order = orderRepository.save(order);
        cart.clearAll();
        return order;
    }

    public List<OrderDto> findAllByUsername(String username) {
        return orderRepository.findAllByUserUsername(username).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
