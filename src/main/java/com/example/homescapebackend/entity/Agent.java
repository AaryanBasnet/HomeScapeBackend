package com.example.homescapebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Agent {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="members_seq_gen")
    @SequenceGenerator(name="members_seq_gen",sequenceName="members_seq",allocationSize=1)
   @Id
    private Integer agentId;
    private String image;
    private String name;
    private String phone;
}
