package ru.skypro.homework.service.impl;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.Model.User;
import ru.skypro.homework.service.PictureService;

public class PictureServiceImpl implements PictureService {

    @Override
    public boolean saveAvatar(User user, MultipartFile avatar) {
        return false;
    }

    @Override
    public boolean saveAdsImage(User user, MultipartFile image) {
        return false;
    }

    @Override
    public MultipartFile getImage(String filepath) {
        return null;
    }
}
