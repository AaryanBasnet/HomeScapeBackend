package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.FileData;
import com.example.homescapebackend.repo.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StorageService {
    @Autowired
    private FileDataRepository fileDataRepository;
    public final String FOLDER_PATH = "D:/HomeScapeBackend/src/main/java/com/example/homescapebackend/images/";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        System.out.println("Attempting to upload image: " + file.getOriginalFilename() + " to " + filePath);

        // Check if file already exists
        List<FileData> existingFiles = fileDataRepository.findByName(file.getOriginalFilename() );
        if (!existingFiles.isEmpty()) {
            System.out.println("Warning: " + existingFiles.size() + " entries found for filename: " + file.getOriginalFilename());
        }

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        if (fileData == null) {
            return null;
        }
        return file.getOriginalFilename();
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        System.out.println("Attempting to download image: " + fileName);
        List<FileData> fileDataList = fileDataRepository.findByName(fileName);
        if (!fileDataList.isEmpty()) {
            // Use the first result if multiple exist
            FileData fileData = fileDataList.get(0);
            String filePath = fileData.getFilePath();
            System.out.println("File path found: " + filePath);
            File file = new File(filePath);
            if (file.exists()) {
                byte[] image = Files.readAllBytes(file.toPath());
                System.out.println("Image successfully read, size: " + image.length + " bytes");
                return image;
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        } else {
            System.out.println("FileData not found for fileName: " + fileName);
        }
        return null;
    }

    public List<String> listAllImages() {
        List<FileData> fileDataList = fileDataRepository.findAll();
        return fileDataList.stream()
                .map(FileData::getName)
                .collect(Collectors.toList());
    }
    public void cleanupDuplicateEntries(String fileName) {
        List<FileData> duplicates = fileDataRepository.findByName(fileName);
        if (duplicates.size() > 1) {
            // Keep the first entry, delete the rest
            for (int i = 1; i < duplicates.size(); i++) {
                fileDataRepository.delete(duplicates.get(i));
            }
            System.out.println("Cleaned up " + (duplicates.size() - 1) + " duplicate entries for " + fileName);
        }
    }
}
