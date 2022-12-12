package com.example.clientapp.repository;

import com.example.clientapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    @Override
    Optional<OrderItem> findById(Integer integer);
}
