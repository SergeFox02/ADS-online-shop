package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.FullAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    Ads findAdsById(int id);

    ResponseWrapperAds getAdsMe();

    FullAds getFullAds(int id);

    AdsDto addAds(CreateAds ads, Image images);
}
