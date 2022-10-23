package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.ImageService;

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
    public ResponseEntity<?> getImage(@PathVariable Integer id){
        logger.info("Call getImage id = " + id);
        Image image = imageService.findImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(image.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.getData());
    }

}
