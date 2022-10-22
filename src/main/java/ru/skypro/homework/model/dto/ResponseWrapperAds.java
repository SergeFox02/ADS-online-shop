package ru.skypro.homework.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class ResponseWrapperAds {

    private int count;
    private Collection<AdsDto> results;

    public ResponseWrapperAds(Collection<AdsDto> result) {
        this.count = result.size();
        this.results = result;
    }

}
