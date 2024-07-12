package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepo extends JpaRepository<Inquiry, Long> {
}
