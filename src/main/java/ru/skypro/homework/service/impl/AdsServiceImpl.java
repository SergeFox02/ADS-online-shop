package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.*;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.model.mapper.CommentsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service for working with Ads
 */
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AdsMapper adsMapper;
    private final CommentsMapper commentsMapper;
    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    /**
     * Get all ads
     *
     * @return all ads
     */
    @Override
    public Collection<AdsDto> getAllAds() {
        logger.info("Call getAllAds");

        return adsRepository.findAll().stream()
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all ads of user
     *
     * @return all ads of user
     */
    @Override
    public Collection<AdsDto> getAdsMe() {
        logger.info("Call getAdsMe");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return adsRepository.findAll().stream()
                .filter(ads -> Objects.equals(ads.getAuthor().getId(), user.getId()))
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());
    }

    /**
     * Get full information of ads
     *
     * @param id ads id
     * @return full ads
     */
    @Override
    public FullAds getFullAds(int id) {
        logger.info("Call getFullAds");
        if (adsRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ads getAds = adsRepository.findById(id).get();

        return adsMapper.toFullAds(getAds, userRepository.findById(getAds.getAuthor().getId()).get());
    }

    /**
     * Add new ads
     *
     * @param ads new ads
     * @param image image of ads
     * @return new ads
     */
    @Override
    public AdsDto addAds(CreateAds ads, Image image) {
        logger.info("Call addAds");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ads response = adsRepository.save(adsMapper.newAds(ads, user, image));

        return adsMapper.toAdsDto(response);
    }

    /**
     * Get comments of ads
     *
     * @param adsId das id
     * @return comments of ads
     */
    @Override
    public Collection<AdsComment> getAdsComments(int adsId) {
        logger.info("Call getAdsComments");
        if (adsRepository.findById(adsId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads is not Found");
        }

        return commentRepository.findAll().stream()
                .filter(c -> c.getAds().getId() == adsId)
                .map(commentsMapper::toAdsComment)
                .collect(Collectors.toList());
    }

    /**
     * Delete ads bu id
     *
     * @param id ads id
     * @return delete ads
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads not found
     * @throws ResponseStatusException return {@code  HttpStatus.FORBIDDEN} if user is not Admin or not enough rights odf user
     */
    @Override
    public Ads deleteAds(int id) {
        logger.info("Call deleteAds");
        if (adsRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads is not Found");
        }
        Ads deleteAds = adsRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (deleteAds.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)){
            adsRepository.deleteById(id);

            return deleteAds;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unavailable to update. It's not your ads!");
    }

    /**
     * Update ads
     *
     * @param id ads id
     * @param ads new ads
     * @return updating ads
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads not found
     * @throws ResponseStatusException return {@code  HttpStatus.FORBIDDEN} if user is not Admin or not enough rights odf user
     */
    @Override
    public AdsDto updateAds(int id, AdsDto ads) {
        logger.info("Call updateAds");
        if (adsRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads is not Found");
        }
        Ads oldAds = adsRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oldAds.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)){
            Ads newAds = new Ads();
            newAds.setAuthor(user);
            newAds.setId(ads.getPk());
            newAds.setDescription(ads.getDescription());
            newAds.setPrice(ads.getPrice());
            newAds.setTitle(ads.getTitle());
            adsRepository.save(newAds);

            return adsMapper.toAdsDto(newAds);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unavailable to update. It's not your ads!");
    }
}
