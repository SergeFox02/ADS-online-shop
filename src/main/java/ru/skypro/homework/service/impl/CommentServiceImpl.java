package ru.skypro.homework.service.impl;

import ru.skypro.homework.Model.Comment;
import ru.skypro.homework.Model.User;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.util.Collection;

public class CommentServiceImpl implements CommentService {

    @Override
    public boolean createComment(Long adsPk, String text, User author, LocalDateTime createdAt) {
        return false;
    }

    @Override
    public Collection<Comment> getComments(Long pk) {
        return null;
    }

    @Override
    public boolean deleteComment(Long ad_pk, Long comment_pk) {
        return false;
    }

    @Override
    public Comment getComment(Long ad_pk, Long comment_pk) {
        return null;
    }

    @Override
    public boolean updateComment(Long adsPk, String text, User author, Long comment_pk) {
        return false;
    }
}
