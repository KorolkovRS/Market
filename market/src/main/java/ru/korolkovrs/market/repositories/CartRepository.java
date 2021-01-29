package ru.korolkovrs.market.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.korolkovrs.market.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
