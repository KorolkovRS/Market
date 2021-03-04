package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.UserDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.services.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/users/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody User user) {
//        log.debug(user.getPassword());
        return new UserDto(userService.addUser(user));
    }

    @PostMapping("/auth/users/addAddress")
    public UserDto addAddress(@RequestBody Long id, @RequestBody Address address) {
        return new UserDto(userService.updateUser(id, address));
    }

    @PostMapping("/auth/users/userInfo")
    public UserDto getUserInfo(@RequestBody String username) {
        log.info(username);
        return userService.getUserById(username).orElseThrow(() -> new ResourceNotFoundException("User " + username + " not exist"));
    }
}
