package ru.skypro.homework.service;

import ru.skypro.homework.Model.Ads;
import ru.skypro.homework.dto.AdsDto;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAllAds();

    Ads findAds(Long id);

}
