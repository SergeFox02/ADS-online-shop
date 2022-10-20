package ru.skypro.homework.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAdsComment {

    private int count;
    private Collection<AdsComment> results;

    public ResponseWrapperAdsComment(Collection<AdsComment> results) {
        this.count = results.size();
        this.results = results;
    }
}
