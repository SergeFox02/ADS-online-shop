package ru.skypro.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdsDto {

    private Integer pk;
    private Integer author;
    private String title;
    private String description;
    private String image;
    private Integer price;

}
