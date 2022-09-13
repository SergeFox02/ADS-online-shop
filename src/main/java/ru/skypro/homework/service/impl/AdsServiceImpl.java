package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.Model.Ads;
import ru.skypro.homework.Model.User;
import ru.skypro.homework.service.AdsService;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    @Override
    public boolean createAd(String title, String description, String image, float price, User author) {
        return false;
    }

    @Override
    public List<Ads> getAllAds() {
        return null;
    }

    @Override
    public Ads getAd(Long pk) {
        return null;
    }

    @Override
    public boolean deleteAd(Long pk) {
        return false;
    }

    @Override
    public boolean updateAd(Long pk) {
        return false;
    }
}
