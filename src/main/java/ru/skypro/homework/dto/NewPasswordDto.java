package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class NewPasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
