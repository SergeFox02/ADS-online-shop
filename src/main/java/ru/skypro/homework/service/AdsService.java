package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    ResponseWrapperAds getAdsMe();

    FullAds getFullAds(int id);

    AdsDto addAds(CreateAds ads, Image images);

    ResponseWrapperAdsComment getAdsComments(int id);

    Ads deleteAds(int id);
}
