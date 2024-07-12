package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.Inquiry;
import com.example.homescapebackend.pojo.InquiryPojo;

import java.util.List;

public interface InquiryService {
    Inquiry saveInquiry(InquiryPojo inquiryPojo, String username);
    List<Inquiry> getAllInquiries();
}
