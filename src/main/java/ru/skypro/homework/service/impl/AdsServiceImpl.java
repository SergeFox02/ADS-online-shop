package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
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
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;


    @Override
    public ResponseWrapperAds getAllAds() {
        Collection<AdsDto> adsDtoCollection = adsRepository.findAll().stream()
                .map(ads -> adsMapper.toAdsDto(ads))
                .collect(Collectors.toList());
        return new ResponseWrapperAds(adsDtoCollection);
    }

    @Override
    public Ads findAds(Long id) {
        return null;
    }

}
