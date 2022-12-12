package com.example.clientapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderItem> items = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "order")
    List<OrderStatus> orderStatuses = new ArrayList<>();

}
