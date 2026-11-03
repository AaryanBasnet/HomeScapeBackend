package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.FileData;
import com.example.homescapebackend.repo.FileDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {
    @Autowired
    private FileDataRepository fileDataRepository;
    @Value("${homescape.upload-dir:uploads/}")
    private String uploadDir;

    /** Folder that holds the uploaded photos, always ending in a slash. Set from homescape.upload-dir. */
    public String FOLDER_PATH = "uploads/";

    @PostConstruct
    void prepareUploadFolder() {
        // Absolute on purpose: MultipartFile.transferTo() resolves a relative path against its own temp folder.
        File folder = new File(uploadDir).getAbsoluteFile();
        folder.mkdirs();
        FOLDER_PATH = folder.toURI().getPath().replaceFirst("^/([A-Za-z]:)", "$1");
    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        // Keep only the file name, so a name like "../x" cannot write outside the upload folder.
        String safeName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String filePath = FOLDER_PATH + safeName;
        System.out.println("Attempting to upload image: " + file.getOriginalFilename() + " to " + filePath);

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
            for (int i = 1; i < duplicates.size(); i++) {
                fileDataRepository.delete(duplicates.get(i));
            }
            System.out.println("Cleaned up " + (duplicates.size() - 1) + " duplicate entries for " + fileName);
        }
    }
}
