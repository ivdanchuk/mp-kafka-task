package com.example.clientapp.controller;

import com.example.clientapp.dto.OrderDTO;
import com.example.clientapp.model.CustomerOrder;
import com.example.clientapp.model.OrderStatusEnum;
import com.example.clientapp.repository.CustomerOrderRepository;
import com.example.clientapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/")
public class OrderController {
    private final CustomerOrderRepository orderRepository;
    private final OrderService orderService;
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @PostMapping("/order")
    public ResponseEntity<Integer> createOrder(@RequestBody OrderDTO dto) {
        CustomerOrder order = orderService.createOrder(dto);
        kafkaTemplate.send("orders", order.getId(),
                OrderStatusEnum.RECEIVED.toString());

        return ResponseEntity.ok(order.getId());
    }

    @GetMapping("/order/{orderId}")
    public String getStatus(@PathVariable Integer orderId) {
        return orderService.getOrderStatus(orderId).getStatus().toString();
    }

}

