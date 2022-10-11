package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.User;

public interface PictureService {

    boolean saveAvatar(User user, MultipartFile avatar);

    String saveAdsImage(User user, MultipartFile image);

    MultipartFile getImage(String filepath);

    MultipartFile getPicture(Long id);

    Long savePicture(MultipartFile picture, Ads ads);

}
