package com.example.homescapebackend.shared;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GlobalApiResponse <T> {
    private String message;
    private T data;
    private Integer statusCode;
}
