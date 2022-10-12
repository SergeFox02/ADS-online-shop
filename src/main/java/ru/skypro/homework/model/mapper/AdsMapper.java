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

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", expression = "java(\"/image/\" + ads.getImage().getId())")
    AdsDto toAdsDto(Ads ads);

    @Mapping(target ="pk", expression = "java(ads.getId())")
    @Mapping(target ="authorFirstName", expression = "java(user.getFirstName())")
    @Mapping(target ="authorLastName", expression = "java(user.getLastName())")
    @Mapping(target = "image", expression = "java(\"http://127.0.0.1:8080/image/\" + ads.getImage().getId())")
    FullAds toFullAds(Ads ads, User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ads newAds(CreateAds createAds, User author, Image image);

}
