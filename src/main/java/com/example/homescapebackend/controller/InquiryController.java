package com.example.homescapebackend.controller;

import com.example.homescapebackend.entity.Inquiry;
import com.example.homescapebackend.pojo.InquiryPojo;
import com.example.homescapebackend.service.InquiryService;
import com.example.homescapebackend.shared.GlobalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<GlobalApiResponse<Inquiry>> createInquiry(@RequestBody InquiryPojo inquiryPojo) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Inquiry inquiry = inquiryService.saveInquiry(inquiryPojo, username);
        GlobalApiResponse<Inquiry> response = GlobalApiResponse.<Inquiry>builder()
                .message("Inquiry created successfully")
                .data(inquiry)
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GlobalApiResponse<List<Inquiry>>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        GlobalApiResponse<List<Inquiry>> response = GlobalApiResponse.<List<Inquiry>>builder()
                .message("All inquiries fetched successfully")
                .data(inquiries)
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthly")
    public ResponseEntity<Map<YearMonth, Map<Integer, Long>>> getInquiriesPerMonth() {
        Map<YearMonth, Map<Integer, Long>> inquiriesPerMonth = inquiryService.getInquiriesPerMonth();
        return new ResponseEntity<>(inquiriesPerMonth, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<GlobalApiResponse<Long>> getInquiryCount() {
        Long count = inquiryService.countInquiries();
        return new ResponseEntity<>(GlobalApiResponse.<Long>builder()
                .data(count)
                .statusCode(HttpStatus.OK.value())
                .message("Total inquiry count retrieved successfully!")
                .build(), HttpStatus.OK);
    }


}
