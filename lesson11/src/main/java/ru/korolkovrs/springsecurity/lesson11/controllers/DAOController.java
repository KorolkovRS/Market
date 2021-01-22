package ru.korolkovrs.springsecurity.lesson11.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korolkovrs.springsecurity.lesson11.entities.Score;
import ru.korolkovrs.springsecurity.lesson11.entities.User;
import ru.korolkovrs.springsecurity.lesson11.repositoryes.ScoreRepository;
import ru.korolkovrs.springsecurity.lesson11.repositoryes.UserRepository;
import ru.korolkovrs.springsecurity.lesson11.services.ScoreService;
import ru.korolkovrs.springsecurity.lesson11.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@Profile("dao")
@RequiredArgsConstructor
@RequestMapping("/app")
public class DAOController {
    private final UserService userService;
    private final ScoreService scoreService;

    @GetMapping
    public String getHelloPage() {
        return "Hello guest!";
    }

    @GetMapping("/score/get/current")
    public String getCurrentUserScore(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("unable to fing user by username: " + principal.getName()));
        return "User: " + user.getUsername() + "; score: " + scoreService.getScoreByUserId(user.getId()).getScore();
    }

    @GetMapping("/score/get/inc")
    public Score incUserScore(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("unable to fing user by username: " + principal.getName()));
        return scoreService.incUserScore(scoreService.getScoreByUserId(user.getId()), user);
    }

    @GetMapping("/score/get/dec")
    public Score decUserScore(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("unable to fing user by username: " + principal.getName()));
        return scoreService.decUserScore(scoreService.getScoreByUserId(user.getId()), user);
    }

    @GetMapping("/score/get/{id}")
    public String getUserScoreById(@PathVariable(name = "id") Long id) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> new RuntimeException("unable to fing user by id: " + id));
        return "User: " + user.getUsername() + "; score: " + scoreService.getScoreByUserId(user.getId()).getScore();
    }
}


// Создайте новый проект с spring-boot + spring-security (все на RestController'ах делаем)
// Форму входа используем стандартную
// Подключите туда DaoAuthentication
// Для каждого пользователя сделайте сущность Score в которой
// указывается некий балл пользователя
// GET .../app/score/inc - увеличивает балл текущего пользователя
// GET .../app/score/dec - уменьшает балл текущего пользователя
// GET .../app/score/get/current - показывает балл вошедшего пользователя
// GET .../app/score/get/{id} - показывает балл пользователя с указанным id (доступно
// только для залогиненных пользователей)
