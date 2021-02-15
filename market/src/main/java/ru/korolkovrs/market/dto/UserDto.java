package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.models.User;

import javax.persistence.Column;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
