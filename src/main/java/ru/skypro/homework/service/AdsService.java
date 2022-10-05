package ru.skypro.homework.service;


import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.User;

import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.entity.Ads;

import java.util.List;

public interface AdsService {

    boolean createAd(String title, String description, String image, float price, User author);

//    List<Ads> getAllAds();

    Ads getAd(Long pk);

    boolean deleteAd(Long pk);

    boolean updateAd(Long pk);

    ResponseWrapperAds getAllAds();

    Ads findAds(Long id);

}