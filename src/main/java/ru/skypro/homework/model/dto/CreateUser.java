package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class CreateUser {

    String email;
    String firstName;
    String lastName;
    String password;
    String phone;

}
