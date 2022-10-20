package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

}
