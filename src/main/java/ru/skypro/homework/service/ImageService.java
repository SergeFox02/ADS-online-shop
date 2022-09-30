package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Model.Image;

import java.io.IOException;

public interface ImageService {

    void upLoadImage(Long id, MultipartFile file) throws IOException;

    Image findImage(Long avatarId);

}
