package com.example.clientapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderStatus {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatusEnum Status;

    private LocalDateTime dateTime;

    @ManyToOne (fetch = FetchType.LAZY)
    private CustomerOrder order;

}
