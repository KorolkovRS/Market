package ru.korolkovrs.market.dto;

import lombok.Data;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Role;
import ru.korolkovrs.market.models.User;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private List<String> addresses;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if (user.getAddresses() != null) {
            this.addresses = user.getAddresses().stream().map(Address::getTitle).collect(Collectors.toList());
        } else {
            this.addresses = new ArrayList<>();
        }
    }
}
