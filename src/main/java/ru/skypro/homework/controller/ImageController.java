package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Transient;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@CrossOrigin(
        value = "http://localhost:3000",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("ads/image")
@RequiredArgsConstructor
public class ImageController {

    Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @GetMapping(value = "/{id}")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        logger.info("Call method downloadAvatar");
        Image image = imageService.findImage(id);

//        Path path = Path.of(image.getFilePath());
//        try (InputStream is = Files.newInputStream(path);
//             OutputStream os = response.getOutputStream();
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
//        ) {
//            response.setContentType(image.getMediaType());
//            response.setContentLength((int) image.getFileSize());
//            response.setStatus(200);
//            bis.transferTo(bos);
//        }
    }
}
