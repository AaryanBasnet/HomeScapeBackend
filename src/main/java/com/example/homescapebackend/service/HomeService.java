package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.Home;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HomeService {
    List<Home> findAll();

    void saveHome(Home home, MultipartFile image) throws IOException;

    void deleteHome(Integer id);

    Optional<Home> findHomeById(Integer id);

    void updateHome(Integer id, Home homeDetails, MultipartFile image) throws IOException;

    byte[] getHomeImage(Integer homeId) throws IOException;

    List<Home> filterHomes(String city, String propertyType, Long minPrice, Long maxPrice);

    List<String> getCities();

    List<String> getPropertyTypes();
}
