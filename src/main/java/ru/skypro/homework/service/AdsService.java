package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAllAds();

    Collection<AdsDto> getAdsMe();

    FullAds getFullAds(int id);

    AdsDto addAds(CreateAds ads, Image images);

    Collection<AdsComment> getAdsComments(int id);

    Ads deleteAds(int id);

    AdsDto updateAds(int id, AdsDto ads);
}
