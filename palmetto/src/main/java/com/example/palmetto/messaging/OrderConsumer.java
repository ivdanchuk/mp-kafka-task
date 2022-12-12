package com.example.palmetto.messaging;

import com.example.palmetto.model.OrderStatusEnum;
import com.example.palmetto.service.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderConsumer {
    private final KafkaTemplate<Integer,String> kafkaTemplate;
    private final PizzaService service;

    @KafkaListener (id = "palmetto1", topics = "orders")
    public void consumeOrder(final ConsumerRecord<Integer,String> record) throws InterruptedException {
        log.info("Get new order with ID: {}, Status: {}",record.key(),record.value());

        kafkaTemplate.send("notification",0,record.key(), OrderStatusEnum.IN_PROGRESS.toString());

        service.processOrder(record.key(), record.value());

        kafkaTemplate.send("notification",1,record.key(), OrderStatusEnum.IN_DELIVERY.toString());
    }
}
