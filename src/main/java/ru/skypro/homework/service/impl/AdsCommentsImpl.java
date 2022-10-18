package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
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
        if (adsRepository.findById(ad_pk).isEmpty()){
            throw new NotFoundException("Ads by id = " + ad_pk + ", is not Found");
        }
        newComment.setAds(adsRepository.findById(ad_pk).get());
        newComment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(newComment);

        return adsComment;
    }

    @Override
    public Comment deleteComment(int ad_pk, int id) throws NotFoundException {
        if (adsRepository.findById(ad_pk).isEmpty() || commentRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads by id = " + ad_pk + ", is not Found");
        }
        Comment deleteComment = commentRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (deleteComment.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)) {
            commentRepository.deleteById(id);

            return deleteComment;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unavailable to update. It's not your ads!");
    }

    @Override
    public AdsComment getComment(int ad_pk, int id) {
        if (adsRepository.findById(ad_pk).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads by id = " + ad_pk + ", is not Found");
        }
        if (commentRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment by id = " + ad_pk + ", is not Found");
        }

        return commentsMapper.toAdsComment(commentRepository.findById(id).get());
    }

    @Override
    public AdsComment updateComment(int ad_pk, int id, AdsComment comment) {
        if (adsRepository.findById(ad_pk).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads by id = " + ad_pk + ", is not Found");
        }
        if (commentRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment by id = " + ad_pk + ", is not Found");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment oldComment = commentRepository.findById(id).get();
        if (oldComment.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)){
            Comment newComment = commentsMapper.toComment(comment);
            newComment.setAuthor(user);
            newComment.setId(comment.getPk());
            newComment.setText(comment.getText());
            newComment.setAds(adsRepository.findById(ad_pk).get());
            newComment.setCreatedAt(LocalDateTime.now());
            commentRepository.save(newComment);

            return commentsMapper.toAdsComment(newComment);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unavailable to update. It's not your ads!");
    }
}
