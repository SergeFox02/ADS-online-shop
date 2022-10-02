package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.RegisterReq;
import ru.skypro.homework.model.dto.Role;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "email")
    User toUser(RegisterReq registerReq);

    User loginReqToUserMapping(LoginReq loginReq);
}
