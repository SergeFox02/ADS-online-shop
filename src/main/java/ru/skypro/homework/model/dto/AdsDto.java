package ru.skypro.homework.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdsDto {

    private Long id;
    private Long author;
    private String title;
    private String description;
    private String image;
    private Long price;

}
