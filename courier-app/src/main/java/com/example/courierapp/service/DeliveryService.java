package com.example.courierapp.service;

import org.springframework.stereotype.Component;

@Component
public class DeliveryService {
    public void deliver () throws InterruptedException {
        Thread.sleep(5000);
    }
}
