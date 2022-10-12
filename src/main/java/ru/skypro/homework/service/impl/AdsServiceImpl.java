package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;

import ru.skypro.homework.model.mapper.CommentsMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;

import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.List;

/**
 * Service for working with Ads
 */
@Service
public class AdsServiceImpl implements AdsService {

    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;
    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public AdsServiceImpl(AdsRepository adsRepository,
                          AdsMapper adsMapper,
                          UserService userService,
                          CommentRepository commentRepository,
                          CommentsMapper commentsMapper) {
        this.adsRepository = adsRepository;
        this.adsMapper = adsMapper;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.commentsMapper = commentsMapper;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public Ads findAdsById(int id) {
        if (!adsRepository.findById((long) id).isPresent()){
            return null;
        }
        return adsRepository.findById((long) id).get();
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .filter(ads -> Objects.equals(ads.getAuthor().getId(), user.getId()))
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());

        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public FullAdsDto getFullAds(int id) {
        if (!adsRepository.findById((long) id).isPresent()){
            return null;
        }
        Ads getAds = adsRepository.findById((long) id).get();
        logger.info("Call get ads and fullAdsMap");
        return adsMapper.toFullAds(getAds, userService.findUserById(getAds.getAuthor().getId()));
    }

    @Override
    public AdsDto addAds(CreateAdsDto ads, Image image) {
        logger.info("Trying to add new ads");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ads newAds = adsMapper.newAds(ads, user, image);
        logger.info("set newAds {}", newAds);
        Ads response = adsRepository.save(newAds);
        logger.info("The ad with pk = {} was saved ", response.getId());

        return adsMapper.toAdsDto(response);
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(int adsId) {
        if (!adsRepository.findById((long) adsId).isPresent()){
            return null;
        }
        Collection<AdsComment> commentDtoCollection = commentRepository.findAll().stream()
                .filter(c -> c.getAds().getId() == adsId)
                .map(commentsMapper::toAdsComment)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsComment(commentDtoCollection);
    }
}
