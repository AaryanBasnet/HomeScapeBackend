package com.example.homescapebackend.controller;

import com.example.homescapebackend.entity.Home;
import com.example.homescapebackend.service.HomeService;
import com.example.homescapebackend.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/get")
    public GlobalApiResponse<List<Home>> getAllHomes() {
        List<Home> homes = homeService.findAll();
        return GlobalApiResponse.<List<Home>>builder()
                .data(homes)
                .statusCode(200)
                .message("Homes retrieved successfully!")
                .build();
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Home> getHomeById(@PathVariable Integer id) {
        Optional<Home> home = homeService.findHomeById(id);
        return home.map(value -> GlobalApiResponse.<Home>builder()
                        .data(value)
                        .statusCode(200)
                        .message("Home retrieved successfully!")
                        .build())
                .orElseGet(() -> GlobalApiResponse.<Home>builder()
                        .statusCode(404)
                        .message("Home not found!")
                        .build());
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Home> createHome(@RequestPart("home") Home home,
                                              @RequestPart("image") MultipartFile image) {
        try {
            homeService.saveHome(home, image);
            return GlobalApiResponse.<Home>builder()
                    .data(home)
                    .statusCode(201)
                    .message("Home created successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Home>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Home> updateHome(@PathVariable Integer id,
                                              @RequestPart("home") Home homeDetails,
                                              @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            homeService.updateHome(id, homeDetails, image);
            Home updatedHome = homeService.findHomeById(id).orElse(null);
            return GlobalApiResponse.<Home>builder()
                    .data(updatedHome)
                    .statusCode(200)
                    .message("Home updated successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Home>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }

    @DeleteMapping("delete/{id}")
    public GlobalApiResponse<Void> deleteHome(@PathVariable Integer id) {
        Optional<Home> home = homeService.findHomeById(id);
        if (home.isPresent()) {
            homeService.deleteHome(id);
            return GlobalApiResponse.<Void>builder()
                    .statusCode(204)
                    .message("Home deleted successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Home not found!")
                    .build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getHomeImage(@PathVariable Integer id) {
        try {
            byte[] imageData = homeService.getHomeImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filter")
    public GlobalApiResponse<List<Home>> filterHomes(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice) {
        // Validate and adjust parameters if necessary
        if ("Location (any)".equals(city) || (city != null && city.isEmpty())) {
            city = null; // Convert default or empty string to null for proper filtering
        }
        if ("Property type (any)".equals(propertyType) || (propertyType != null && propertyType.isEmpty())) {
            propertyType = null; // Convert default or empty string to null for proper filtering
        }

        // Adjust maxPrice if needed to avoid very large values
        if (maxPrice == null || maxPrice <= 0) {
            maxPrice = 100000000000L; // Set a reasonable default max price
        }

        List<Home> homes = homeService.filterHomes(city, propertyType, minPrice, maxPrice);
        return GlobalApiResponse.<List<Home>>builder()
                .data(homes)
                .statusCode(200)
                .message("Filtered homes retrieved successfully!")
                .build();
    }


    @GetMapping("/cities")
    public GlobalApiResponse<List<String>> getCities() {
        List<String> cities = homeService.getCities();
        return GlobalApiResponse.<List<String>>builder()
                .data(cities)
                .statusCode(200)
                .message("Cities retrieved successfully!")
                .build();
    }

    @GetMapping("/properties")
    public GlobalApiResponse<List<String>> getPropertyTypes() {
        List<String> propertyTypes = homeService.getPropertyTypes();
        return GlobalApiResponse.<List<String>>builder()
                .data(propertyTypes)
                .statusCode(200)
                .message("Property types retrieved successfully!")
                .build();
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getHomeCount() {
        return GlobalApiResponse.<Long>builder()
                .data(homeService.HomeCount())
                .statusCode(200)
                .message("Total home count retrieved successfully!")
                .build();
    }
}
