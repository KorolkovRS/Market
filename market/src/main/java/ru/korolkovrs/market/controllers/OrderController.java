package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.CartDto;
import ru.korolkovrs.market.dto.OrderDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Order;
import ru.korolkovrs.market.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrderFromCart(Principal principal, @RequestParam UUID cartUuid, @RequestParam String address) {
        String username = principal.getName();
        log.debug(address);
        Address adr = new Address(address);
        return new OrderDto(orderService.saveOrder(cartUuid, username, adr));
    }


    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderDto(order);
    }

    @GetMapping
    public List<OrderDto> findAllByUsername(Principal principal) {
        String username = principal.getName();
        List<OrderDto> orderDtoList = orderService.findAllByUsername(username);
        return orderDtoList;
    }
}
