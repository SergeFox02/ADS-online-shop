package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.UserDto;

@Mapper
public interface UserDtoMapper {


    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "username", source = "email")
    UserDto UserToUserDto(User user);

    @Mapping(source = "username", target = "email")
    User UserDtoToUser(UserDto userDto);


}
