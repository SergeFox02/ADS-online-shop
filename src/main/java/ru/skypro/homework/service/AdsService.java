package ru.skypro.homework.service;

import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

import java.util.Collection;

public interface AdsService {

    Collection<AdsDto> getAllAds();

    Ads findAds(Long id);

}
