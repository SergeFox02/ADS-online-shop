package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.skypro.homework.model.dto.CreateAdsDto;
import ru.skypro.homework.model.dto.FullAdsDto;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Comment;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AdsMapper {

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", expression = "java(\"/image/\" + ads.getImage().getId())")
    AdsDto toAdsDto(Ads ads);

    @Mapping(target = "author", source = "author")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "image", source = "image")
    @Mapping(expression = "java(adsDto.getId())", target = "id")
    Ads fromAdsDto(AdsDto adsDto, User author, Image image, List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ads fromCreatingAds(CreateAdsDto CreateAdsDto, User author, Image image);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.username")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", expression = "java(\"http://127.0.0.1:8080/image/\" + ads.getImage().getPk())")
    FullAdsDto getAdsDto(Ads ads);

    @Mapping(target ="pk", expression = "java(ads.getId())")
    @Mapping(target ="authorFirstName", expression = "java(user.getFirstName())")
    @Mapping(target ="authorLastName", expression = "java(user.getLastName())")
    @Mapping(target = "image", expression = "java(\"http://127.0.0.1:8080/image/\" + ads.getImage().getId())")
    FullAdsDto toFullAds(Ads ads, User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Ads newAds(CreateAdsDto createAds, User author, Image image);
}
