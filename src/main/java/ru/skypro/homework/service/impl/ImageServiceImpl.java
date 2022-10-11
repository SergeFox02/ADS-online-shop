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

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AdsService adsService;

    private final ImageMapper imageMapper;

    @Value(value = "${images.dir.path}")
    private String imagesDir;

    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl(ImageRepository imageRepository,
                            AdsService adsService,
                            ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.adsService = adsService;
        this.imageMapper = imageMapper;
    }

    @Override
    public Image upLoadImage(MultipartFile file) throws IOException {
        logger.info("Uploading new image");
        return imageRepository.saveAndFlush(convertImageFromFile(file));
    }

    @Override
    public Image findImage(Long imageId) {
        logger.info("Attempting to find image by id = {}", imageId);
        return imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    logger.warn("There is no image with such id: {}", imageId);
                    return new NoSuchElementException("There is no image with such id found!");
                });
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        logger.info("Deleting image with id: {}", id);
        imageRepository.deleteById(id);
    }

    @Override
    public Image updateImage(Long id, MultipartFile file) {
        return imageRepository.saveAndFlush(convertImageFromFile(file));
    }

    private Image convertImageFromFile(MultipartFile file) {
        logger.info("Converting image from file");
        Image image = new Image();
        try {
            image = imageMapper.getImageFromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
