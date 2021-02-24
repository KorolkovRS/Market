package ru.korolkovrs.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korolkovrs.market.dto.UserDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Address;
import ru.korolkovrs.market.models.Role;
import ru.korolkovrs.market.models.User;
import ru.korolkovrs.market.repositories.RoleRepository;
import ru.korolkovrs.market.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public User addUser(User user) {
        if (!userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            String rawPassword = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.getOne(1L));
            user.setRoles(roles);
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User with username " + user.getUsername() + " exist");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User updateUser(Long id, Address address) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getOne(id);
            if (!user.getAddresses().contains(address)) {
                Collection<Address> addresses = user.getAddresses();
                addresses.add(address);
                user.setAddresses(addresses);
            }
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User update fail. User with id" + id + " not exist");
        }
    }

    public Optional<UserDto> getUserById(String username) {
        return userRepository.findByUsername(username).map(UserDto::new);
    }
}
