package io.jmix.bookstore.order.calendar.mapper;

import io.jmix.bookstore.order.calendar.dto.OrderCalendarEventDto;
import io.jmix.bookstore.order.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderCalendarEventDtoMapper {

    public List<OrderCalendarEventDto> transformOrderToEvent(Order order) {
        if (order.getOrderDate() == null) {
            return new ArrayList<>();
        }

        List<OrderCalendarEventDto> events = new ArrayList<>();
        events.add(createEvent(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderDate().plusDays(1).atStartOfDay().toLocalDate(),
                "Order #" + order.getOrderNumber(),
                order.getRepresentationText()
        ));
        if (order.getShippingDate() != null) {
            events.add(createEvent(
                    order.getId(),
                    order.getOrderNumber(),
                    order.getShippingDate(),
                    order.getShippingDate().plusDays(1).atStartOfDay().toLocalDate(),
                    "Shipping for Order #" + order.getOrderNumber(),
                    "Shipping Address: " + order.getShippingAddress().toString()
            ));
        }
        return events;
    }

    private OrderCalendarEventDto createEvent(Object id,
                                              Long orderNumber,
                                              LocalDate startDateTime,
                                              LocalDate endDateTime,
                                              String title,
                                              String description) {
        OrderCalendarEventDto event = new OrderCalendarEventDto();
        event.setEventId(id);
        event.setOrderNumber(orderNumber);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        event.setTitle(title);
        event.setDescription(description);
        return event;
    }

}
