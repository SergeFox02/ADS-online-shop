package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Ads;

import java.util.Collection;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    AdsDto findAds(Long id);

    Ads findAdsById(long id);

    CreateAds createAds(CreateAds createAds);

    ResponseWrapperAds getAdsMe();

}
