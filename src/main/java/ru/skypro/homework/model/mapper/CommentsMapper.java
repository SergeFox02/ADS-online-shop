package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    @Mapping(target ="author", source = "author.id")
    @Mapping(target ="dateTime", source = "createdAt")
    @Mapping(target ="pk", source = "id")
    AdsComment toAdsComment(Comment comment);

    @Mapping(target ="author.id", source = "author")
    @Mapping(target ="createdAt", source = "dateTime")
    @Mapping(target ="id", source = "pk")
    Comment toComment(AdsComment Adscomment);
}
