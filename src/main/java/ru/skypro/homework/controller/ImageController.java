package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.ImageService;

import java.io.*;

@RestController
@CrossOrigin(
        value = "http://localhost:3000",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

    Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){
        logger.info("Call method downloadAvatar");
        Image image = imageService.findImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(image.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.getData());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(@RequestParam MultipartFile image) throws IOException {
        logger.info("Call method upLoadAvatar");
        if (image.getSize() > 1024 * 600) {
            logger.warn("Warning: image is to big");
            return ResponseEntity.badRequest().body("File is to big");
        }
        imageService.addImage(image);
        return ResponseEntity.ok().build();
    }
}
