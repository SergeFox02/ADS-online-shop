package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.User;

import ru.skypro.homework.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skypro.homework.model.dto.ResponseWrapperAds;

import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import java.util.List;

/**
 * Service for working with Ads
 */
@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;

    public AdsServiceImpl(AdsRepository adsRepository,
                          AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.adsMapper = adsMapper;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(adsMapper::toAdsDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public Ads findAds(Long id) {
        return null;
    }

    @Override
    public boolean createAd(String title, String description, String image, float price, User author) {
        return false;
    }

//    @Override
//    public List<Ads> getAllAds() {
//        return null;
//    }


    @Override
    public Ads getAd(Long pk) {
        return null;
    }

    @Override
    public boolean deleteAd(Long pk) {
        return false;
    }

    @Override
    public boolean updateAd(Long pk) {
        return false;
    }
}
