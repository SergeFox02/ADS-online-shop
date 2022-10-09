package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public boolean saveAvatar(User user, MultipartFile avatar) {
        return false;
    }

    @Override
    public String saveAdsImage(User user, MultipartFile image) {
        String path = "";
        return path;
    }

    @Override
    public MultipartFile getImage(String filepath) {
        return null;
    }
}
