package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.FullAds;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.User;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "id", target = "pk")
    AdsDto toAdsDto(Ads ads);

    Ads toAds(CreateAds createAds);

    @Mapping(source = "ads.id", target ="pk")
    @Mapping(source = "user.firstName", target ="authorFirstName")
    @Mapping(source = "user.lastName", target ="authorLastName")
    FullAds toFullAds(Ads ads, User user);

}
