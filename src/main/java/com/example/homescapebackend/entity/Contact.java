package com.example.homescapebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String message;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


}
