package ru.korolkovrs.springsecurity.lesson11.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.korolkovrs.springsecurity.lesson11.entities.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
