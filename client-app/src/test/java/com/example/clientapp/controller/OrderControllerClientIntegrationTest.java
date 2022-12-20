package com.example.clientapp.controller;

import com.example.clientapp.dto.OrderDTO;
import com.example.clientapp.model.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerClientIntegrationTest {
    @Container
    static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @LocalServerPort
    int localPort;

    @DynamicPropertySource
    static void loadProps(final DynamicPropertyRegistry registry) {
        log.info(KAFKA_CONTAINER.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetNewOrderId() throws InterruptedException {
        String uri = "http://localhost:"+ localPort +"/order";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(1);
        orderDTO.setItemId(1);
        HttpEntity<OrderDTO> httpEntity = new HttpEntity<>(orderDTO);

        Integer orderId = restTemplate.exchange(uri, HttpMethod.POST,httpEntity, Integer.class).getBody();
        assertThat(orderId).isNotNull();
        log.info(String.valueOf(orderId));

        uri = "http://localhost:"+ localPort +"/order/"+orderId;
        String orderStatus = restTemplate.exchange(uri, HttpMethod.GET,httpEntity, String.class).getBody();
        assertThat(orderStatus).isEqualTo(OrderStatusEnum.RECEIVED.toString());
        log.info((orderStatus));
    }
}
