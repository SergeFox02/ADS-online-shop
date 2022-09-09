package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skypro.homework.Model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findUserById(Long userId);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long userId);

}
