package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;

import java.io.IOException;

public interface ImageService {

    Image upLoadImage(MultipartFile file) throws IOException;

    Image findImage(Long Id);

    void deleteImage(Long id);

    Image updateImage(Long id, MultipartFile file);

}
