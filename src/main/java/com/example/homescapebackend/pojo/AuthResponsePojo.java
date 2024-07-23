package com.example.homescapebackend.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponsePojo {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private List<String> roles;
}
