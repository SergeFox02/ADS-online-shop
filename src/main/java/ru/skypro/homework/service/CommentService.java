package ru.skypro.homework.service;

import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CommentService {

    boolean createComment(Long adsPk, String text, User author, LocalDateTime createdAt);

    Collection<Comment> getComments(Long pk);

    boolean deleteComment(Long ad_pk, Long comment_pk);

    Comment getComment(Long ad_pk, Long comment_pk);

    boolean updateComment(Long adsPk, String text, User author, Long comment_pk);

}
