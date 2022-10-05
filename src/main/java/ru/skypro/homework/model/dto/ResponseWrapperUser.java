package ru.skypro.homework.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperUser {

    private int count;
    private Collection<UserDto> results;

    public ResponseWrapperUser(Collection<UserDto> results) {
        this.count = results.size();
        this.results = results;
    }
}
