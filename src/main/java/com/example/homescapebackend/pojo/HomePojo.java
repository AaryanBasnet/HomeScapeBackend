package com.example.homescapebackend.pojo;

import com.example.homescapebackend.entity.Agent;

import com.example.homescapebackend.entity.FileData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomePojo {

    private Integer homeId;
    private String type;
    private String name;
    private String description;
    private FileData imageData;
    private String city;
    private String address;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer surface;
    private Long price;
    private Agent agent;

}
