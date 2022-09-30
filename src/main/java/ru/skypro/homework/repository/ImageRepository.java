package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.Model.Image;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Override
    Optional<Image> findById(Long id);
}
