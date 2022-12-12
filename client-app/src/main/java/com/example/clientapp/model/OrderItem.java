package com.example.clientapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "items")
    private List<CustomerOrder> orders;

}
