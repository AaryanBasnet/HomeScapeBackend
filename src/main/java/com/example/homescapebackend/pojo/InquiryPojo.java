package com.example.homescapebackend.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryPojo {
    private String name;
    private String email;
    private String phone;
    private String message;
    private Long homeId;
}
