package com.example.homescapebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "about")

public class About {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="members_seq_gen")
    @SequenceGenerator(name="members_seq_gen",sequenceName="members_seq",allocationSize=1)
    @Id
    private Integer aboutId;
    @Column(name = "mission",  length = 1000)

    private String OurMission;
    @Column(name = "vision",  length = 1000)

    private String OurVision;


}
