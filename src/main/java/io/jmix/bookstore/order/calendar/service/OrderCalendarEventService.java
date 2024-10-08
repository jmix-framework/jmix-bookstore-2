package io.jmix.bookstore.order.calendar.service;

import io.jmix.bookstore.order.calendar.dto.OrderCalendarEventDto;
import io.jmix.bookstore.order.calendar.mapper.OrderCalendarEventDtoMapper;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.fake.OrderFakeGenerator;
import io.jmix.bookstore.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class OrderCalendarEventService {

    private final OrderRepository orderRepository;
    private final OrderFakeGenerator orderFakeGenerator;
    private final OrderCalendarEventDtoMapper orderCalendarEventDtoMapper;

    public OrderCalendarEventService(OrderRepository orderRepository,
                                     OrderFakeGenerator orderFakeGenerator, OrderCalendarEventDtoMapper orderCalendarEventDtoMapper) {
        this.orderRepository = orderRepository;
        this.orderFakeGenerator = orderFakeGenerator;
        this.orderCalendarEventDtoMapper = orderCalendarEventDtoMapper;
    }

    public List<OrderCalendarEventDto> loadAllOrderEvents() {
        List<Order> fakeOrders = orderFakeGenerator.generateRandomOrdersWithData(3000);
        List<Order> realOrders = StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .toList();

        var result = new ArrayList<Order>();
        result.addAll(realOrders);
        result.addAll(fakeOrders);

        return result.stream()
                .map(orderCalendarEventDtoMapper::transformOrderToEvent)
                .flatMap(Collection::stream)
                .toList();
    }
}
