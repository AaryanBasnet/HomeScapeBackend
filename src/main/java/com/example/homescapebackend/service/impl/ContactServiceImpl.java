package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.Contact;
import com.example.homescapebackend.repo.ContactRepo;
import com.example.homescapebackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact sendMessage(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public List<Contact> getAllMessages() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getMessageById(Long id) {
        return contactRepo.findById(id).orElse(null);
    }
}
