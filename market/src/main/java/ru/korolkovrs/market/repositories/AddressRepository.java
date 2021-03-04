package ru.korolkovrs.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korolkovrs.market.models.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByTitle(String title);

    Optional<Address> findByTitle(String title);
}
