package ru.skypro.homework.mapper;

import ru.skypro.homework.Model.User;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;

public interface UserMapper {
    User regReqToUserMapping(RegisterReq registerReq, Role role);

    User loginReqToUserMapping(LoginReq loginReq);

    UserDto userToDtoMapper(User user);
}
