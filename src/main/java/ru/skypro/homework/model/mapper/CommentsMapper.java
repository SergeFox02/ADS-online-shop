package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    @Mapping(target ="author", source = "author.id")
    @Mapping(target ="dateTime", source = "createdAt")
    AdsComment toAdsComment(Comment comment);

    @Mapping(target ="author.id", source = "author")
    @Mapping(target ="createdAt", source = "dateTime")
    Comment toComment(AdsComment Adscomment);
}
