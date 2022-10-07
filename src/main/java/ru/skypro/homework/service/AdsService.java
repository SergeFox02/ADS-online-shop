package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.dto.AdsDto;

import java.util.Collection;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    AdsDto findAds(Long id);

    CreateAds createAds(CreateAds createAds);

    ResponseWrapperAds getAdsMe();

}
