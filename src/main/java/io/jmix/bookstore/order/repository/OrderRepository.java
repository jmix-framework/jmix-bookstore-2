package io.jmix.bookstore.order.repository;

import io.jmix.bookstore.order.entity.Order;
import io.jmix.core.repository.JmixDataRepository;

import java.util.UUID;

public interface OrderRepository extends JmixDataRepository<Order, UUID> {
}