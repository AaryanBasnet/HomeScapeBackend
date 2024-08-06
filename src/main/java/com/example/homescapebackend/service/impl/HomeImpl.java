package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.FileData;
import com.example.homescapebackend.entity.Home;
import com.example.homescapebackend.repo.HomeRepo;
import com.example.homescapebackend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeImpl implements HomeService {

    private final HomeRepo homeRepository;
    private final StorageService storageService;

    @Override
    public List<Home> findAll() {
        return homeRepository.findAll();
    }

    @Override
    public void saveHome(Home home, MultipartFile image) throws IOException {
        String fileName = storageService.uploadImageToFileSystem(image);
        FileData imageData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        home.setImageData(imageData);
        homeRepository.save(home);
    }

    @Override
    public void deleteHome(Integer id) {
        homeRepository.deleteById(id);
    }

    @Override
    public void updateHome(Integer id, Home homeDetails, MultipartFile image) throws IOException {
        Optional<Home> optionalHome = homeRepository.findById(id);
        if (optionalHome.isPresent()) {
            Home home = optionalHome.get();
            home.setName(homeDetails.getName());
            home.setAddress(homeDetails.getAddress());
            home.setPrice(homeDetails.getPrice());
            home.setBathrooms(homeDetails.getBathrooms());
            home.setBedrooms(homeDetails.getBedrooms());
            home.setSurface(homeDetails.getSurface());
            home.setCity(homeDetails.getCity());
            home.setDescription(homeDetails.getDescription());
            home.setType(homeDetails.getType());
            home.setAgent(homeDetails.getAgent());

            if (image != null && !image.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(image);
                FileData imageData = FileData.builder()
                        .name(fileName)
                        .type(image.getContentType())
                        .filePath(storageService.FOLDER_PATH + fileName)
                        .build();
                home.setImageData(imageData);
            }

            homeRepository.save(home);
        }
    }

    @Override
    public Optional<Home> findHomeById(Integer id) {
        return homeRepository.findById(id);
    }

    @Override
    public byte[] getHomeImage(Integer homeId) throws IOException {
        Optional<Home> optionalHome = homeRepository.findById(homeId);
        if (optionalHome.isPresent() && optionalHome.get().getImageData() != null) {
            String fileName = optionalHome.get().getImageData().getName();
            if (fileName != null) {
                return storageService.downloadImageFromFileSystem(fileName);
            }
        }
        return null;
    }

    @Override
    public List<Home> filterHomes(String city, String propertyType, Long minPrice, Long maxPrice) {
        if ("Location (any)".equals(city)) {
            city = null;
        }
        if ("Property type (any)".equals(propertyType)) {
            propertyType = null;
        }
        if (minPrice != null && minPrice <= 0) {
            minPrice = null;
        }
        if (maxPrice != null && maxPrice <= 0) {
            maxPrice = null;
        }

        return homeRepository.filterHomes(city, propertyType, minPrice, maxPrice);
    }


    @Override
    public List<String> getCities() {
        return homeRepository.findDistinctCities();
    }

    @Override
    public List<String> getPropertyTypes() {
        return homeRepository.findDistinctPropertyTypes();
    }

    @Override
    public Long HomeCount() {
        return homeRepository.count();
    }
}
