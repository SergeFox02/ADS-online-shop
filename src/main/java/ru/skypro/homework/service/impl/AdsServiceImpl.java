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

    @Override
    public ResponseWrapperAds getAllAds() {
        logger.info("Call getAllAds");
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());

        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        logger.info("Call getAdsMe");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .filter(ads -> Objects.equals(ads.getAuthor().getId(), user.getId()))
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());

        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public FullAds getFullAds(int id) {
        logger.info("Call getFullAds");
        if (adsRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ads getAds = adsRepository.findById(id).get();

        return adsMapper.toFullAds(getAds, userRepository.findById(getAds.getAuthor().getId()).get());
    }

    @Override
    public AdsDto addAds(CreateAds ads, Image image) {
        logger.info("Call addAds");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ads response = adsRepository.save(adsMapper.newAds(ads, user, image));

        return adsMapper.toAdsDto(response);
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(int adsId) {
        logger.info("Call getAdsComments");
        if (adsRepository.findById(adsId).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ads is not Found");
        }
        Collection<AdsComment> commentDtoCollection = commentRepository.findAll().stream()
                .filter(c -> c.getAds().getId() == adsId)
                .map(commentsMapper::toAdsComment)
                .collect(Collectors.toList());

        return new ResponseWrapperAdsComment(commentDtoCollection);
    }

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
