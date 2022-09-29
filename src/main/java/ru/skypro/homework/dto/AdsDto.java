package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDto {

    private Long pk;
    private Long author;
    private String title;
    private String description;
    private String image;
    private Float price;

}
