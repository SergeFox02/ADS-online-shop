package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.model.dto.CreateUser;
import ru.skypro.homework.model.dto.NewPassword;
import ru.skypro.homework.model.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean isPresent(String username);

    UserDto getUserDto(int id);

    CreateUser addUser(CreateUser user);

    UserDto updateUser(UserDto user);

    NewPassword setPassword(NewPassword newPassword);

}
