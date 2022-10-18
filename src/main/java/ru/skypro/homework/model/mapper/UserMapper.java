package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.CreateUser;
import ru.skypro.homework.model.dto.UserDto;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.RegisterReq;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", source = "username")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    User toUser(RegisterReq registerReq);

    UserDto toUserDto(User user);

    CreateUser toCreateUser(User user);

    User toUser(CreateUser Createuser);

    User toUser(UserDto userDto);
}
