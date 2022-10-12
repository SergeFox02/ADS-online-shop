package ru.skypro.homework.model.dto;

import lombok.Data;

@Data
public class AdsDto {

    private Integer pk;
    private Integer author;
    private String title;
    private String description;
    private String image;
    private Integer price;

}
