package ru.skypro.homework.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsComment {

    private Long author;
    private Long pk;
    private LocalDateTime createAt;
    private String text;

}
