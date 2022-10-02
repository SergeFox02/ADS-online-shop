package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = new User();
        if (userRepository.findUserByEmail(email).isPresent()) {
            user = userRepository.findUserByEmail(email).get();
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {

        if (isPresent(user.getUsername())) {
            return false;
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
        }

        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPresent(String username) {
        return userRepository.findUserByEmail(username).isPresent();
    }
}
