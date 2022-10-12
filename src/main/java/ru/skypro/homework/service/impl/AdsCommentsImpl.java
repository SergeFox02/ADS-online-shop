package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;
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
}
