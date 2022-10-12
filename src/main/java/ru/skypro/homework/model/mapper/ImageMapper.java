package ru.skypro.homework.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;

import java.io.IOException;

@Mapper(componentModel = "spring")
@Component
public interface ImageMapper {

    @Mapping(target = "filePath", expression = "java(file.getResource().getFilename())")
    @Mapping(target = "fileSize", expression = "java((long) (file.getSize()))")
    @Mapping(target = "mediaType", expression = "java(file.getContentType())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", expression = "java(file.getBytes())")
    Image getImageFromFile(MultipartFile file) throws IOException;
}
