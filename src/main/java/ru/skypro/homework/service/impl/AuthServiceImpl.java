package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.RegisterReq;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    private UserMapper userMapper;

    public AuthServiceImpl(UserDetailsManager manager,
                           UserRepository userRepository,
                           UserService userService) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!userService.isPresent(userName)) {
            return false;
        }
        UserDetails userDetails = userService.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        return encoder.matches(password, encryptedPassword);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            logger.info("Not register new user? because user exist");
            return false;
        }
        logger.info("Register new user");
        User newUser = userMapper.toUser(registerReq);
        newUser.setRole(Role.USER);
        newUser.setPassword(encoder.encode(registerReq.getPassword()));
        manager.createUser(newUser);
        userRepository.save(newUser);
        return true;
    }
}
