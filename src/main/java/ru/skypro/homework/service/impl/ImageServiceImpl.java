package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.mapper.ImageMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.*;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AdsService adsService;

    @Value(value = "${images.dir.path")
    private String imagesDir;

    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl(ImageRepository imageRepository, AdsService adsService, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.adsService = adsService;
        this.imageMapper = imageMapper;
    }

    @Override
    public Image addImage(MultipartFile file) throws IOException {
        Image image;
        try {
            image = imageMapper.fromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image imageSave = imageRepository.save(image);
        logger.info("New image was saved with id = {}", imageSave.getId());

        return imageSave;
    }

    @Override
    public Image findImage(Long imageId) {
        logger.info("Was invoked method for find image by id = {}", imageId);
        return imageRepository.findById(imageId).orElse(new Image());
    }

    private Image getImages(MultipartFile mediaTypeImages) {
        logger.info("Trying to add new image");
        Image image;
        try {
            image = imageMapper.fromFile(mediaTypeImages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}
