package ru.skypro.homework.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAds {

    private int count;
    private Collection<AdsDto> result;

    public ResponseWrapperAds(Collection<AdsDto> result) {
        this.count = result.size();
        this.result = result;
    }
}
