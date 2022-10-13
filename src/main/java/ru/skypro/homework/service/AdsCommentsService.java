package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;

public interface AdsCommentsService {

    AdsComment addComment(int ad_pk, AdsComment adsComment);

    Comment deleteComment(int ad_pk, int id);

    AdsComment getComment(int ad_pk, int id);
}
