package ru.skypro.homework.service;

import ru.skypro.homework.Model.Ads;
import ru.skypro.homework.Model.User;

import java.util.List;

public interface AdsService {

    boolean createAd(String title, String description, String image, float price, User author);

    List<Ads> getAllAds();

    Ads getAd(Long pk);

    boolean deleteAd(Long pk);

    boolean updateAd(Long pk);


}
