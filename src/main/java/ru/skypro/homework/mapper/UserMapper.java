package ru.skypro.homework.mapper;

import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.RegisterReq;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.dto.UserDto;

public interface UserMapper {
    User regReqToUserMapping(RegisterReq registerReq, Role role);

    User loginReqToUserMapping(LoginReq loginReq);

    UserDto userToDtoMapper(User user);
}
