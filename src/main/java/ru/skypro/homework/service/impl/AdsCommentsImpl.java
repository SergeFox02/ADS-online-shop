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

/**
 * Service for working with AdsComments.
 */

@Service
@RequiredArgsConstructor
public class AdsCommentsImpl implements AdsCommentsService {

    private final CommentRepository commentRepository;
    private final CommentsMapper commentsMapper;
    private final AdsRepository adsRepository;

    /**
     * Add Comment in Ads with id = ad_pk
     *
     * @param ad_pk id of Ads
     * @param adsComment comment of Ads
     * @return Adding comment
     *
     * @throws NotFoundException if Comment by id = ad_pk not found
     */
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
        adsComment.setPk(newComment.getId());

        return adsComment;
    }

    /**
     * Delete comment by id, of ads ad_pk
     *
     * @param ad_pk id of ads
     * @param id id of comment
     * @return removed comment
     * @throws NotFoundException if ads by ad_pk not found
     * @throws ResponseStatusException return {@code  HttpStatus.FORBIDDEN} if user is not Admin or not enough rights odf user
     */
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

    /**
     * Get comment of Ads
     *
     * @param ad_pk ads id
     * @param id of comment
     * @return comment by id of ads(ad_pk)
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads or comment not found
     */
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

    /**
     * Update ads comment
     *
     * @param ad_pk id of ads
     * @param id comment {@code id}
     * @param comment update comment of ads
     * @return update comment
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads or comment not found
     * @throws ResponseStatusException return {@code  HttpStatus.FORBIDDEN} if user is not Admin or not enough rights odf user
     */
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
