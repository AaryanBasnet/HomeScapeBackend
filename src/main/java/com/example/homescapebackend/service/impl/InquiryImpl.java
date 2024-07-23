package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.entity.Home;
import com.example.homescapebackend.entity.Inquiry;
import com.example.homescapebackend.pojo.InquiryPojo;
import com.example.homescapebackend.repo.CustomerRepo;
import com.example.homescapebackend.repo.HomeRepo;
import com.example.homescapebackend.repo.InquiryRepo;

import com.example.homescapebackend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InquiryImpl implements InquiryService {

    private final InquiryRepo inquiryRepository;
    private final CustomerRepo customerRepo;
    private final HomeRepo homeRepository;



    @Override
    public Inquiry saveInquiry(InquiryPojo inquiryPojo, String username) {
        Customer customer = customerRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Customer not found"));
        Home home = homeRepository.findById(Math.toIntExact(inquiryPojo.getHomeId())).orElseThrow(() -> new RuntimeException("Home not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setName(inquiryPojo.getName());
        inquiry.setEmail(inquiryPojo.getEmail());
        inquiry.setPhone(inquiryPojo.getPhone());
        inquiry.setMessage(inquiryPojo.getMessage());
        inquiry.setCustomer(customer);
        inquiry.setHome(home);

        return inquiryRepository.save(inquiry);
    }

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    @Override
    public Map<YearMonth, Map<Integer, Long>> getInquiriesPerMonth() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return inquiries.stream()
                .collect(Collectors.groupingBy(
                        inquiry -> YearMonth.from(inquiry.getCreatedDate()),
                        Collectors.groupingBy(
                                inquiry -> inquiry.getHome().getHomeId(),
                                Collectors.counting()
                        )
                ));
    }

    public Long countInquiries() {
        return inquiryRepository.count();
    }


}
