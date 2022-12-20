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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class OrderControllerFullIntegrationTest {
    @LocalServerPort
    private int localPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetNewOrderId() throws InterruptedException {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(1);
        orderDTO.setItemId(1);

        HttpEntity<OrderDTO> httpEntity = new HttpEntity<>(orderDTO);
        String uri = "http://localhost:" + localPort + "/order";
        Integer orderId = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Integer.class).getBody();
        assertThat(orderId).isNotNull();

        uri = "http://localhost:" + localPort + "/order/" + orderId;
        String orderStatus = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
        assertThat(orderStatus).isEqualTo(OrderStatusEnum.IN_PROGRESS.toString());

        Thread.sleep(5000);
        uri = "http://localhost:" + localPort + "/order/" + orderId;
        orderStatus = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
        assertThat(orderStatus).isEqualTo(OrderStatusEnum.IN_DELIVERY.toString());

        Thread.sleep(5000);
        uri = "http://localhost:" + localPort + "/order/" + orderId;
        orderStatus = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
        assertThat(orderStatus).isEqualTo(OrderStatusEnum.DELIVERED.toString());

    }
}
