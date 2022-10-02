package ru.skypro.homework.model.mapper;

import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

public interface AdsMapper {

    AdsDto toAdsDto(Ads ads);

}
