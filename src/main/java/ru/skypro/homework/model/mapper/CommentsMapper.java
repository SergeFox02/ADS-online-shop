package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.skypro.homework.model.dto.AdsComment;
import ru.skypro.homework.model.entity.Comment;

@Mapper(componentModel = "spring")
@Component
public interface CommentsMapper {

    @Mapping(target ="author", source = "author.id")
    @Mapping(target ="dateTime", source = "createdAt")
    AdsComment toAdsComment(Comment comment);
}
