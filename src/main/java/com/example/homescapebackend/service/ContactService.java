package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.Contact;

import java.util.List;

public interface ContactService {
    Contact sendMessage(Contact contact);
    List<Contact> getAllMessages();
    Contact getMessageById(Long id);
}
