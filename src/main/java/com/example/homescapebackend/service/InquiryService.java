package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.Inquiry;
import com.example.homescapebackend.pojo.InquiryPojo;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface InquiryService {
    Inquiry saveInquiry(InquiryPojo inquiryPojo, String username);
    List<Inquiry> getAllInquiries();
    Long countInquiries();
    Map<YearMonth, Map<Integer, Long>> getInquiriesPerMonth();

}
