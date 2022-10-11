package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.FullAds;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(\"/image/\" + ads.getImage().getId())")
    AdsDto toAdsDto(Ads ads);

    Ads toAds(CreateAds createAds);

    @Mapping(source = "user.id", target ="pk")
    @Mapping(source = "user.firstName", target ="authorFirstName")
    @Mapping(source = "user.lastName", target ="authorLastName")
    @Mapping(target = "image", expression = "java(\"http://127.0.0.1:8080/ads/image/\" + ads.getImage().getId())")
    FullAds toFullAds(Ads ads, User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ads newAds(CreateAds createAds, User author, Image image);

}
