package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "author.id", target = "author")
    AdsDto toAdsDto(Ads ads);

}
