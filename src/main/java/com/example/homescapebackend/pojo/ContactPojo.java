package com.example.homescapebackend.pojo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactPojo {
    private String name;
    private String email;
    private String message;
    private Long customerId;


}
