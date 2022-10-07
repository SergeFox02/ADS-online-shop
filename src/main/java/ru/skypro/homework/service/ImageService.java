package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;

import java.io.IOException;

public interface ImageService {

    void upLoadImage(Long id, MultipartFile file) throws IOException;

    Image findImage(Long id);

}
