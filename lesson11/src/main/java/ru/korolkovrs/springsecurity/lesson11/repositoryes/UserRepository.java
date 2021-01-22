package ru.korolkovrs.springsecurity.lesson11.repositoryes;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.korolkovrs.springsecurity.lesson11.entities.User;

import java.util.Optional;

@Repository
@Profile("dao")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
