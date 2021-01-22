package ru.korolkovrs.springsecurity.lesson11.repositoryes;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.korolkovrs.springsecurity.lesson11.entities.Role;

@Repository
@Profile("dao")
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
