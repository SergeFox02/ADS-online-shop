package ru.skypro.homework.service;


import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;

import ru.skypro.homework.model.entity.Ads;

import java.util.List;

public interface AdsService {

    Ads findAdsById(int id);

    ResponseWrapperAds getAllAds();

    ResponseWrapperAds getAdsMe();

    FullAdsDto getFullAds(int id);

    AdsDto addAds(CreateAdsDto ads, Image images);

    ResponseWrapperAdsComment getAdsComments(int id);


}