package ru.skypro.homework.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsComment {

    private Long pk;
    private Long author;
    private LocalDateTime dateTime;
    private String text;

}
