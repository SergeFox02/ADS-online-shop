package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "id", target = "author")
    @Mapping(source = "id", target = "pk")
    AdsDto toAdsDto(Ads ads);

}
