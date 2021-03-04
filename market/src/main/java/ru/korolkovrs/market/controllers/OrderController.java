package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.OrderDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.services.OrderService;
import ru.korolkovrs.market.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/createOrder")
    public OrderDto createOrder(Principal principal, @RequestBody String addressStr) {
        String username = principal.getName();
        log.debug(addressStr);
        Address address = new Address(addressStr);
        return new OrderDto(orderService.saveOrder(username, address));
    }

    @GetMapping("/allOrders")
    public List<OrderDto> findAllByUsername(Principal principal) {
        String username = principal.getName();
        List<OrderDto> orderDtoList = orderService.findAllByUsername(username);
        return orderDtoList;
    }
}
