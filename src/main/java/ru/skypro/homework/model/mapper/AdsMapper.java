package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class );

    @Mapping(source = "pk", target = "authorId")
    AdsDto toAdsDto(Ads ads);
}
