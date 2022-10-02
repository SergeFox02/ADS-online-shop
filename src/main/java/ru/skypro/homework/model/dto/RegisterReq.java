package ru.skypro.homework.model.dto;

import lombok.Data;
import ru.skypro.homework.model.entity.Role;

@Data
public class RegisterReq {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

}
