package ru.skypro.homework.mapper.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.RegisterReq;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

@Service
public class RegisterReqToUserMapper implements UserMapper {

    private final UserService userService;

    public RegisterReqToUserMapper(UserService userService) {
        this.userService = userService;
    }

    public User regReqToUserMapping(RegisterReq registerReq, Role role) {
        User user = new User();

        if (userService.findUserByEmail(registerReq.getUsername()).isPresent()) {
            user = userService.findUserByEmail(registerReq.getUsername()).get();
        } else {
            user.setEmail(registerReq.getUsername());
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
            if (registerReq.getRole() == null) {
                user.setRole(Role.USER);
            } else {
                user.setRole(role);
            }
            if (registerReq.getPhone() == null) {
                user.setPhone("Не задано");
            } else {
                user.setPhone(registerReq.getPhone());
            }
            userService.saveUser(user);
        }
        return user;
    }

    public UserDto userToDtoMapper(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    @Override
    public User loginReqToUserMapping(LoginReq loginReq) {

        return null;
    }
}
