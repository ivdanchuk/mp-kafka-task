package com.example.courierapp.messaging;

import com.example.courierapp.model.OrderStatusEnum;
import com.example.courierapp.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderConsumer {
    private final KafkaTemplate<Integer,String> kafkaTemplate;
    private final DeliveryService service;

    @KafkaListener(id = "delivery1", topics = "notification", topicPartitions = @TopicPartition(topic = "notification",
            partitions = "1"))
    public void consumeOrders(final ConsumerRecord<Integer,String> record) throws InterruptedException {
        log.info("Get order with ID: {}, Status: {}",record.key(),record.value());

        service.deliver();

        kafkaTemplate.send("notification",0,record.key(), OrderStatusEnum.DELIVERED.toString());
    }
}
