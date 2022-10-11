package ru.skypro.homework.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Role;
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

    private final UserMapper userMapper;

    public AuthServiceImpl(UserDetailsManager manager,
                           UserRepository userRepository,
                           PasswordEncoder encoder,
                           UserService userService,
                           UserMapper userMapper) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        return encoder.matches(password, encryptedPassword);
    }

    @Override
    public boolean register(RegisterReq registerReq) {
        if (manager.userExists(registerReq.getUsername())) {
            logger.info("User already registered!");
            return false;
        }

        UserDetails userDetails = User.builder()
                .password(encoder.encode(registerReq.getPassword()))
                .username(registerReq.getUsername())
                .roles(Role.USER.name())
                .build();

        manager.createUser(userDetails);
        logger.info("Registering new user");

        UserDto userDto = new UserDto();
        userDto.setEmail(userDetails.getUsername());
        userDto.setFirstName(registerReq.getFirstName());
        userDto.setLastName(registerReq.getLastName());
        userDto.setPhone(registerReq.getPhone());

        userService.saveUser(userDto, userDetails.getPassword());

        return true;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }
}
