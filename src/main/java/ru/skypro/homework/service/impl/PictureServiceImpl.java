package ru.skypro.homework.service.impl;

import org.springframework.boot.jta.atomikos.AtomikosDependsOnBeanFactoryPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Ads;
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

    @Override
    public MultipartFile getPicture(Long id) {
        // TODO: 09.10.2022 дописать логику
        return null;
    }

    public Long savePicture(MultipartFile picture, Ads ads) {
        // TODO: 09.10.2022 дописать логику
        return null;
    }
}
