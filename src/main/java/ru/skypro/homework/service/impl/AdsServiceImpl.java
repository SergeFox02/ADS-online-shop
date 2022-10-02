package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service for working with Ads
 */
@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    @Autowired
    private AdsMapper adsMapper;

    public AdsServiceImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    @Override
    public Collection<AdsDto> getAllAds() {
        Collection<Ads> allAds = adsRepository.findAll();
        return allAds.stream()
                .map(s -> adsMapper.toAdsDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public Ads findAds(Long id) {
        return null;
    }

}
