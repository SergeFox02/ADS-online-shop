package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class AdsDto {

    private Long pk;
    private Long author;
    private String title;
    private String description;
    private String image;
    private Long price;

}
