package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.LoginReq;
import ru.skypro.homework.model.dto.*;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(source = "username", target = "email")
    User toUser(RegisterReq registerReq);

    User toUser(UserDto userDto);

    User toUser(LoginReq loginReq);
}
