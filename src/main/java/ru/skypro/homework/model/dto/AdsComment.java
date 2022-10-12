package ru.skypro.homework.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsComment {

    private Integer pk;
    private Integer author;
    private LocalDateTime dateTime;
    private String text;

}
