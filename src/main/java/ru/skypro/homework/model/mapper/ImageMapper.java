package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "filePath", expression = "java(file.getResource().getFilename())")
    @Mapping(target = "fileSize", expression = "java((int) (file.getSize()))")
    @Mapping(target = "mediaType", expression = "java(file.getContentType())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", expression = "java(file.getBytes())")
    Image fromFile (MultipartFile file) throws IOException;
}
