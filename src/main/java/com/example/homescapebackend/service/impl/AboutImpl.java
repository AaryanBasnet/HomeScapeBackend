package com.example.homescapebackend.service.impl;


import com.example.homescapebackend.entity.About;
import com.example.homescapebackend.pojo.AboutPojo;
import com.example.homescapebackend.repo.AboutRepo;
import com.example.homescapebackend.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AboutImpl implements AboutService {

    private final AboutRepo aboutRepo;

    @Autowired
    public AboutImpl(AboutRepo aboutRepo) {
        this.aboutRepo = aboutRepo;
    }

    @Override
    public void updateAbout(Integer id, AboutPojo aboutPojo) {
        if (aboutRepo.count() == 0) {
            throw new RuntimeException("No About entry exists to update");
        }
        About about = aboutRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("About with id " + id + " not found"));
        about.setOurMission(aboutPojo.getOurMission());
        about.setOurVision(aboutPojo.getOurVision());
        aboutRepo.save(about);
    }

    @Override
    public void deleteAbout(Integer id) {
        if (aboutRepo.count() == 0) {
            throw new RuntimeException("No About entry exists to delete");
        }
        if (aboutRepo.existsById(id)) {
            aboutRepo.deleteById(id);
        } else {
            throw new RuntimeException("About with id " + id + " not found");
        }
    }

    @Override
    public void createAbout(AboutPojo aboutPojo) {
        if (aboutRepo.count() > 0) {
            throw new RuntimeException("An About entry already exists");
        }
        About about = new About();
        about.setOurMission(aboutPojo.getOurMission());
        about.setOurVision(aboutPojo.getOurVision());
        aboutRepo.save(about);
    }

    @Override
    public AboutPojo getAbout(Integer id) {
        About about = aboutRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("About with id " + id + " not found"));
        return new AboutPojo(about.getAboutId(), about.getOurMission(), about.getOurVision());
    }

    @Override
    public List<AboutPojo> getAllAbout() {
        if (aboutRepo.count() == 0) {
            return List.of();
        }
        List<About> aboutList = aboutRepo.findAll();
        return aboutList.stream()
                .map(about -> new AboutPojo(about.getAboutId(), about.getOurMission(), about.getOurVision()))
                .toList();
    }
}
