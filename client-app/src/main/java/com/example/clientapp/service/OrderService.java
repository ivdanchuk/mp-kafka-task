package com.example.clientapp.service;

import com.example.clientapp.dto.OrderDTO;
import com.example.clientapp.model.*;
import com.example.clientapp.repository.OrderItemRepository;
import com.example.clientapp.repository.CustomerOrderRepository;
import com.example.clientapp.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusRepository orderStatusRepository;

    public CustomerOrder createOrder (OrderDTO dto){

        OrderItem orderItem = orderItemRepository.findById(dto.getItemId()).orElseThrow();

        CustomerOrder order = new CustomerOrder();
        order.setItems(List.of(orderItem));
        order.setOrderStatuses(List.of(createOrderStatus(order,OrderStatusEnum.RECEIVED)));

        customerOrderRepository.save(order);
        return order;
    }

    public OrderStatus addOrderStatus(String key, String value){

        CustomerOrder order = customerOrderRepository.findById(Integer.parseInt(key)).orElseThrow();

        OrderStatusEnum status =
                Arrays.stream(OrderStatusEnum.values())
                        .filter(s -> value.equals(s.toString()))
                        .findFirst()
                        .orElseThrow();
        OrderStatus orderStatus = createOrderStatus(order,status);
        return orderStatusRepository.save(orderStatus);
    }

    public OrderStatus createOrderStatus(CustomerOrder order, OrderStatusEnum status){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus(status);
        orderStatus.setDateTime(LocalDateTime.now());
        orderStatus.setOrder(order);
        return orderStatus;
    }

    public OrderStatus getOrderStatus(Integer orderId){
        CustomerOrder order = customerOrderRepository.findById(orderId).orElseThrow();
        OrderStatus orderStatus = order.getOrderStatuses()
                .stream()
                .sorted(Comparator.comparingInt(OrderStatus::getId).reversed())
                .findFirst()
                .orElseThrow(()-> new RuntimeException(String.format("Status of order with ID=%d not found",orderId)));
        return orderStatus;
    }

}

