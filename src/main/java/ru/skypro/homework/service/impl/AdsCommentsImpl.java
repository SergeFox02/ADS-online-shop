package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.CommentsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsCommentsService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdsCommentsImpl implements AdsCommentsService {

    private final CommentRepository commentRepository;
    private final CommentsMapper commentsMapper;
    private final AdsRepository adsRepository;

    @Override
    public AdsComment addComment(int ad_pk, AdsComment adsComment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = commentsMapper.toComment(adsComment);
        newComment.setAuthor(user);
        newComment.setAds(adsRepository.getById(ad_pk));
        newComment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(newComment);
        return adsComment;
    }

    @Override
    public Comment deleteComment(int ad_pk, int id) {
        if (adsRepository.findById(ad_pk).isEmpty() || commentRepository.findById(id).isEmpty()){
            return null;
        }
        Comment deleteComment = commentRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (deleteComment.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            commentRepository.deleteById(id);
            return deleteComment;
        }
        return null;
    }

    @Override
    public AdsComment getComment(int ad_pk, int id) {
        if (adsRepository.findById(ad_pk).isEmpty() || commentRepository.findById(id).isEmpty()){
            return null;
        }
        return commentsMapper.toAdsComment(commentRepository.findById(id).get());
    }
}
