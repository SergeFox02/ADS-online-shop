package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service for working with Ads
 */
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
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
    public AdsDto findAds(Long id) {
        logger.info("Was invoked method for find student by id = {}", id);
        if (!adsRepository.findById(id).isPresent()){
            return null;
        }
        return adsMapper.toAdsDto(adsRepository.findById(id).get());
    }

    @Override
    public Ads findAdsById(long id) {
        if (!adsRepository.findById(id).isPresent()){
            return null;
        }
        return adsRepository.findById(id).get();
    }

    @Override
    public CreateAds createAds(CreateAds createAds) {
        logger.info("call create user in AdsService");
        Ads newAds = adsMapper.toAds(createAds);
        newAds.setAuthor((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        adsRepository.save(newAds);
        return createAds;
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        findAdsByAuthor(user.getId())
        Collection<AdsDto> adsDtoCollection = adsRepository.findAdsByAuthor(user.getId()).stream()
                .map(ads -> adsMapper.toAdsDto(ads))
                .collect(Collectors.toList());

        return new ResponseWrapperAds(adsDtoCollection);
    }

}
