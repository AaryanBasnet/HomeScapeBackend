package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {
}
