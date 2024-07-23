package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.About;
import com.example.homescapebackend.pojo.AboutPojo;

import java.util.List;

public interface AboutService {

    void updateAbout(Integer id, AboutPojo aboutPojo);

    void deleteAbout(Integer id);

    void createAbout(AboutPojo aboutPojo);

    AboutPojo getAbout(Integer id);

    List<AboutPojo> getAllAbout();
}