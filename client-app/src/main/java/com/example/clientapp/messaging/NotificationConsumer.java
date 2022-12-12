package com.example.clientapp.messaging;

import com.example.clientapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationConsumer {
    private final KafkaTemplate<Integer, String> template;
    private final OrderService orderService;

    @KafkaListener(id = "client", topics = "notification")
    public void consumeNotification(final ConsumerRecord<Integer, String> record) {
        log.info("Received on topic {}, offset {}, partition {}: {}={}",
                record.topic(),
                record.offset(),
                record.partition(),
                record.key(), record.value());

        orderService.addOrderStatus(record.key(), record.value());
    }
}
