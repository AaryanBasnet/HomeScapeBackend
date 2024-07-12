package com.example.homescapebackend.controller;

import com.example.homescapebackend.service.impl.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final StorageService storageService;

    @PostMapping("/fileSystem")
    public ResponseEntity<String> uploadImageToFileSystem(@RequestParam("images") MultipartFile file) {
        logger.info("Received request to upload image: {}", file.getOriginalFilename());
        try {
            String fileName = storageService.uploadImageToFileSystem(file);
            logger.info("Image uploaded successfully: {}", fileName);
            return ResponseEntity.status(HttpStatus.OK).body(fileName);
        } catch (Exception e) {
            logger.error("Image upload failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/fileSystem/{fileName:.+}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) {
        logger.info("Received request to download image: {}", fileName);
        try {
            byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
            if (imageData != null) {
                logger.info("Image found: {}", fileName);
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageData);
            } else {
                logger.warn("Image not found: {}", fileName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (IOException e) {
            logger.error("Error while fetching image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while fetching image: " + e.getMessage());
        }
    }

    @GetMapping("/fileSystem")
    public ResponseEntity<List<String>> listAllImages() {
        logger.info("Received request to list all images");
        try {
            List<String> fileNames = storageService.listAllImages();
            logger.info("Images found: {}", fileNames.size());
            return ResponseEntity.status(HttpStatus.OK).body(fileNames);
        } catch (Exception e) {
            logger.error("Error while listing images: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
