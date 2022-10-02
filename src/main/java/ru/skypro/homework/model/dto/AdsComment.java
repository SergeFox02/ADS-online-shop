package ru.skypro.homework.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsComment {

    private Long pk;
    private Long authorId;
    private LocalDateTime dateTime;
    private String text;

}
