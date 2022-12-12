package com.example.palmetto.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    public void processOrder(Integer orderId, String status) throws InterruptedException {
        Thread.sleep(5000);
    }
}
