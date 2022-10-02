package ru.skypro.homework.model.mapper;

import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.RegisterReq;
import ru.skypro.homework.model.dto.Role;

public interface UserMapper {
    User regReqToUserMapping(RegisterReq registerReq, Role role);

    User loginReqToUserMapping(LoginReq loginReq);
}
