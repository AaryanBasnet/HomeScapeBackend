package com.example.homescapebackend;



import com.example.homescapebackend.entity.FileData;
import com.example.homescapebackend.entity.Home;
import com.example.homescapebackend.repo.HomeRepo;

import com.example.homescapebackend.service.impl.HomeImpl;
import com.example.homescapebackend.service.impl.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeTest {

    @Mock
    private HomeRepo homeRepo;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private HomeImpl homeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Home home = new Home();
        when(homeRepo.findAll()).thenReturn(List.of(home));

        List<Home> homes = homeService.findAll();

        assertNotNull(homes);
        assertFalse(homes.isEmpty());
        assertEquals(1, homes.size());
        assertEquals(home, homes.get(0));
    }

    @Test
    public void testSaveHome() throws IOException {
        Home home = new Home();
        MultipartFile image = mock(MultipartFile.class);
        String fileName = "test-image.png";

        when(storageService.uploadImageToFileSystem(image)).thenReturn(fileName);

        FileData fileData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        homeService.saveHome(home, image);

        verify(storageService, times(1)).uploadImageToFileSystem(image);
        verify(homeRepo, times(1)).save(home);
        assertEquals(fileData, home.getImageData());
    }

    @Test
    public void testDeleteHome() {
        Integer homeId = 1;

        homeService.deleteHome(homeId);

        verify(homeRepo, times(1)).deleteById(homeId);
    }

    @Test
    public void testUpdateHome() throws IOException {
        Integer homeId = 1;
        Home homeDetails = new Home();
        MultipartFile image = mock(MultipartFile.class);
        String fileName = "updated-image.png";

        Home existingHome = new Home();
        existingHome.setImageData(FileData.builder().name("old-image.png").build());

        when(homeRepo.findById(homeId)).thenReturn(Optional.of(existingHome));
        when(storageService.uploadImageToFileSystem(image)).thenReturn(fileName);

        FileData fileData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();

        homeService.updateHome(homeId, homeDetails, image);

        verify(homeRepo, times(1)).save(existingHome);
        assertEquals(fileData, existingHome.getImageData());
    }

    @Test
    public void testFindHomeById() {
        Integer homeId = 1;
        Home home = new Home();
        when(homeRepo.findById(homeId)).thenReturn(Optional.of(home));

        Optional<Home> foundHome = homeService.findHomeById(homeId);

        assertTrue(foundHome.isPresent());
        assertEquals(home, foundHome.get());
    }








    @Test
    public void testHomeCount() {
        Long count = 10L;
        when(homeRepo.count()).thenReturn(count);

        Long homeCount = homeService.HomeCount();

        assertEquals(count, homeCount);
    }
}
