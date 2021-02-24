package ru.korolkovrs.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korolkovrs.market.beans.Cart;
import ru.korolkovrs.market.dto.OrderDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Order;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.repositories.AddressRepository;
import ru.korolkovrs.market.repositories.OrderRepository;
import ru.korolkovrs.market.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Cart cart;

    @Transactional
    public Order saveOrder(String username, Address address) {
        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Empty cart");
        }

        User user = userService.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User %s not found", username)));

        if (!addressRepository.existsByTitle(address.getTitle())) {
            address = addressRepository.save(address);
        }
        address = addressRepository.findByTitle(address.getTitle()).get();
        Collection<Address> addresses = user.getAddresses();
        if (addresses == null || !addresses.contains(address)) {
            userService.updateUser(user.getId(), address);
        }

        Order order = new Order(cart, user, address);
        order = orderRepository.save(order);
        cart.clearAll();
        log.info(user.getUsername());
        return order;
    }

    public List<OrderDto> findAllByUsername(String username) {
        return orderRepository.findAllByUserUsername(username).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
