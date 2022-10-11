package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.FullAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.UserService;

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
    private final UserService userService;
    private final AdsMapper adsMapper;

    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    @Override
    public ResponseWrapperAds getAllAds() {
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(ads -> adsMapper.toAdsDto(ads))
                .collect(Collectors.toList());
        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public Ads findAdsById(int id) {
        if (adsRepository.findById(id).isEmpty()){
            return null;
        }
        return adsRepository.findById(id).get();
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
}
