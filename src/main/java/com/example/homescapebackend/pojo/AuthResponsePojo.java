package com.example.homescapebackend.pojo;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponsePojo {
    private String accessToken;
    private Integer userId;
    private List<String> roles;


}
