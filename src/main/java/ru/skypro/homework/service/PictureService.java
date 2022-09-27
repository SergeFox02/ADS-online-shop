package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Model.User;

public interface PictureService {

    boolean saveAvatar(User user, MultipartFile avatar);

    String saveAdsImage(User user, MultipartFile image);

    MultipartFile getImage(String filepath);

}
