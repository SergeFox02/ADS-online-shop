package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

import java.util.Collection;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    Ads findAds(Long id);

}
