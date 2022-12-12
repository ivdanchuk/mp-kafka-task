package com.example.clientapp.repository;

import com.example.clientapp.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Integer> {
}
