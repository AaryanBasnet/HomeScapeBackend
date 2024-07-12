package com.example.homescapebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentPojo {
    private Integer agentId;
    private String image;
    private String name;
    private String phone;
}
