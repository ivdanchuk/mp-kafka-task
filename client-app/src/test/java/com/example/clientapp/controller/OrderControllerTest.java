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
class OrderControllerTest {
    @LocalServerPort
    private int localPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetNewOrderId() {
        String uri = "http://localhost:" + localPort + "/order";
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setQuantity(1);
        orderDTO.setItemId(1);
        HttpEntity<OrderDTO> httpEntity = new HttpEntity<>(orderDTO);

        Integer orderId = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Integer.class).getBody();
        assertThat(orderId.intValue());
        log.info(String.valueOf(orderId));

        uri = "http://localhost:" + localPort + "/order/" + orderId;
        String orderStatus = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody().toString();
        assertThat(orderStatus.equalsIgnoreCase(OrderStatusEnum.DELIVERED.toString()));
        log.info(orderStatus);
    }
}
