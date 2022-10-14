package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(ads -> adsMapper.toAdsDto(ads))
                .collect(Collectors.toList());
        return new ResponseWrapperAds(adsDtoCollection);
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
    public FullAds getFullAds(int id) {
        if (adsRepository.findById(id).isEmpty()){
            return null;
        }
        Ads getAds = adsRepository.findById(id).get();
        logger.info("Call get ads and fullAdsMap");
        return adsMapper.toFullAds(getAds, userRepository.findById(getAds.getAuthor().getId()).get());
    }

    @Override
    public AdsDto addAds(CreateAds ads, Image image) {
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
        if (adsRepository.findById(adsId).isEmpty()){
            return null;
        }
        Collection<AdsComment> commentDtoCollection = commentRepository.findAll().stream()
                .filter(c -> c.getAds().getId() == adsId)
                .map(commentsMapper::toAdsComment)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsComment(commentDtoCollection);
    }

    @Override
    public Ads deleteAds(int id) {
        if (adsRepository.findById(id).isEmpty() ){
            return null;
        }
        Ads deleteAds = adsRepository.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (deleteAds.getAuthor().equals(user) || user.getRole().equals(Role.ADMIN)){
            adsRepository.deleteById(id);
            return deleteAds;
        }
        return null;
    }

    @Override
    public AdsDto updateAds(int id, AdsDto ads) {
        if (adsRepository.findById(id).isEmpty() ){
            return null;
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
//            newAds.setImage(); здесь надо написать добавление картинки из adsDto
            adsRepository.save(newAds);
            return adsMapper.toAdsDto(newAds);
        }
        return null;
    }
}
