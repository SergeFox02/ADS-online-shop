package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.dto.UserDto;

@Mapper
public interface UserDtoMapper {

    UserDto UserToUserDto(User user);

    User UserDtoToUser(UserDto userDto);

    User regReqToUserMapping(RegisterReq registerReq);

    User loginReqToUserMapping(LoginReq loginReq);

    UserDto userToDtoMapper(User user);


}
