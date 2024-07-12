package com.example.homescapebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "home_table")
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_seq_gen")
    @SequenceGenerator(name = "home_seq_gen", sequenceName = "home_seq", allocationSize = 1)
    private Integer homeId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_data_id", referencedColumnName = "id")
    private FileData imageData;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "surface", nullable = false)
    private Integer surface;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;
}
