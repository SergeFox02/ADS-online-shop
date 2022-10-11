package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.skypro.homework.model.entity.Role.USER;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(new User());
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
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User saveUser(UserDto userDto, String password) {
        logger.info("Updating user with username: {}", userDto.getUsername());

            User userToSave = userRepository.findUserByUsername(userDto.getUsername())
                    .orElse(new User());
            userToSave.setUsername(userDto.getUsername());
            userToSave.setPassword(password);
            userToSave.setFirstName(userDto.getFirstName());
            userToSave.setLastName(userDto.getLastName());
            userToSave.setPhone(userDto.getPhone());
            userToSave.setRole(USER);
            return userRepository.saveAndFlush(userToSave);

    }

    @Override
    public boolean isPresent(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public UserDto toUserDto(User user) {
        return userMapper.toUserDto(user);
    }
}
