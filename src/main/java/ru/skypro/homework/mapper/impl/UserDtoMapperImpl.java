package ru.skypro.homework.mapper.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mapper.UserDtoMapper;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.model.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

@Service
@Primary
public class UserDtoMapperImpl implements UserDtoMapper, UserMapper {

    private final UserService userService;

    private final PasswordEncoder encoder;

    public UserDtoMapperImpl(UserService userService) {
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDto UserToUserDto(User user) {
        return null;
    }


    @Override
    public User UserDtoToUser(UserDto userDto) {
        return null;
    }

    @Override
    public User regReqToUserMapping(RegisterReq registerReq) {
        User user = new User();

        if (userService.findUserByEmail(registerReq.getUsername()).isPresent()) {
            user = userService.findUserByEmail(registerReq.getUsername()).get();
        } else {
            user.setUsername(registerReq.getUsername());
            if (registerReq.getFirstName() == null) {
                user.setFirstName("Не задано");
            } else {
                user.setFirstName(registerReq.getFirstName());
            }
            if (registerReq.getLastName() == null) {
                user.setLastName("Не задано");
            } else {
                user.setLastName(registerReq.getLastName());
            }
            user.setPassword(registerReq.getPassword());
            user.setRole(Role.USER);

            if (registerReq.getPhone() == null) {
                user.setPhone("Не задано");
            } else {
                user.setPhone(registerReq.getPhone());
            }
            userService.saveUser(user);
        }
        return user;
    }

    @Override
    public User toUser(RegisterReq registerReq) {
        User user = new User();

        if (userService.findUserByEmail(registerReq.getUsername()).isPresent()) {
            user = userService.findUserByEmail(registerReq.getUsername()).get();
        } else {
            user.setUsername(registerReq.getUsername());
            if (registerReq.getFirstName() == null) {
                user.setFirstName("Не задано");
            } else {
                user.setFirstName(registerReq.getFirstName());
            }
            if (registerReq.getLastName() == null) {
                user.setLastName("Не задано");
            } else {
                user.setLastName(registerReq.getLastName());
            }
            user.setPassword(registerReq.getPassword());
            user.setRole(Role.USER);

            if (registerReq.getPhone() == null) {
                user.setPhone("Не задано");
            } else {
                user.setPhone(registerReq.getPhone());
            }
            userService.saveUser(user);
        }
        return user;
    }

    @Override
    public User toUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));

        userService.saveUser(user);
        return user;
    }

    @Override
    public User toUser(LoginReq loginReq) {

        return null;
    }

    public UserDto userToDtoMapper(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getUsername());
        userDto.setPhone(user.getPhone());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }

    @Override
    public User loginReqToUserMapping(LoginReq loginReq) {

        return null;
    }
}
