package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.model.dto.CreateUser;
import ru.skypro.homework.model.dto.NewPassword;
import ru.skypro.homework.model.dto.ResponseWrapperUser;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Optional<User> findUserByEmail(String email);

    User findUserById(int userId);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(int userId);

    boolean isPresent(String username);

    ResponseWrapperUser getUsers();

    UserDto getUserDto(int id);

    CreateUser addUser(CreateUser user);

    UserDto updateUser(UserDto user);

    NewPassword setPassword(NewPassword newPassword);

}
