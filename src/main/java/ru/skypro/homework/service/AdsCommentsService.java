package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.AdsComment;

public interface AdsCommentsService {

    AdsComment addComment(int ad_pk, AdsComment adsComment);
}
