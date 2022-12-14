package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.mapper.ImageMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.*;

/**
 * Service for working with images
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    /**
     * Add new image
     *
     * @param file of image
     * @return new image
     * @throws IOException if exception Input/Output
     */
    @Override
    public Image addImage(MultipartFile file) throws IOException {
        Image image;
        try {
            image = imageMapper.fromFile(file);
        } catch (IOException e) {
            throw new IOException(e);
        }
        Image imageSave = imageRepository.save(image);
        logger.info("New image was saved with id = {}", imageSave.getId());

        return imageSave;
    }

    /**
     * Find image by id
     *
     * @param imageId image id
     * @return finding image
     */
    @Override
    public Image findImage(int imageId) {
        logger.info("Was invoked method for find image by id = {}", imageId);
        return imageRepository.findById(imageId).orElse(new Image());
    }
}
