package ru.korolkovrs.market;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.korolkovrs.market.beans.Cart;
import ru.korolkovrs.market.dto.OrderDto;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Order;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.repositories.AddressRepository;
import ru.korolkovrs.market.repositories.OrderRepository;
import ru.korolkovrs.market.services.OrderService;
import ru.korolkovrs.market.services.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private Cart cart;

    @Test
    public void testFindAllByUsername() {
        User bob = new User();
        bob.setUsername("Bob");
        Address address = new Address();
        address.setTitle("Moscow");

        Order o1 = new Order();
        o1.setId(1L);
        o1.setUser(bob);
        o1.setAddress(address);
        o1.setTotalPrice(600);

        Order o2 = new Order();
        o2.setId(3L);
        o2.setUser(bob);
        o2.setAddress(address);
        o2.setTotalPrice(456);

        Order o3 = new Order();
        o3.setId(8L);
        o3.setUser(bob);
        o3.setAddress(address);
        o3.setTotalPrice(6040);

        List<Order> list = Arrays.asList(o3, o2, o3);

        Mockito
                .doReturn(list)
                .when(orderRepository)
                .findAllByUserUsername("Bob");

        List<OrderDto> orders = orderService.findAllByUsername("Bob");
        Mockito.verify(orderRepository, Mockito.times(1))
                .findAllByUserUsername(ArgumentMatchers.eq("Bob"));
        Assertions.assertEquals(list.stream().map(OrderDto::new).collect(Collectors.toList()), orders);

    }
}
