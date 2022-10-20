package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class NewPassword {

    private String currentPassword;
    private String newPassword;

}
