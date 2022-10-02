package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class FullAds {

    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private Byte image;
    private String phone;
    private Double price;
    private String title;

}
