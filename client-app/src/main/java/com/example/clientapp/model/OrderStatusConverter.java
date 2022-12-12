package com.example.clientapp.model;

import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class OrderStatusConverter implements AttributeConverter <OrderStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatusEnum orderStatuses) {
        if (orderStatuses!=null) {
            return orderStatuses.toString();
        }
        return null;
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String column) {
        if (StringUtils.hasLength(column)){
            return Arrays.stream(OrderStatusEnum.values())
                    .filter(s -> column.equals(s.toString()))
                    .findFirst()
                    .orElseThrow();
        }
        return null;
    }
}
