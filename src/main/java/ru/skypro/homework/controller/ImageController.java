package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getImage(@PathVariable Long id) {
        Image image = imageService.findImage(id);
        return image.getData();
    }

    @PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] addImage(@RequestBody MultipartFile file) throws IOException {
        Image image = imageService.upLoadImage(file);

        return image.getData();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] updateImage(@RequestBody MultipartFile file, @PathVariable Long id) {
        Image image = imageService.updateImage(id, file);

        return image.getData();
    }
}
