package io.jmix.bookstore.order.test_support;

import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.test_support.AddressData;
import io.jmix.bookstore.entity.test_support.AddressMapper;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import net.datafaker.Faker;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class Orders
        implements TestDataProvisioning<OrderData, OrderData.OrderDataBuilder, Order> {

    private static final String DEFAULT_SHIPPING_ADDRESS_POST_CODE = "12345";
    private static final String DEFAULT_SHIPPING_ADDRESS_CITY = "City";
    private static final String DEFAULT_SHIPPING_ADDRESS_STREET = "Street";
    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.CONFIRMED;
    @Autowired
    OrderRepository orderRepository;
    @Autowired @Lazy
    OrderLines orderLines;
    @Autowired
    Customers customers;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressMapper addressMapper;
    public static final LocalDate DEFAULT_ORDER_DATE = LocalDate.now().plusDays(1);


    @Override
    public OrderData.OrderDataBuilder defaultData() {
        return OrderData.builder()
                .orderNumber(new Faker().number().numberBetween(0L, 100_000_000L))
                .orderDate(DEFAULT_ORDER_DATE)
                .customer(customers.createDefault())
                .shippingAddress(defaultShippingAddress())
                .status(DEFAULT_ORDER_STATUS)
                .orderLines(List.of());
    }

    private Address defaultShippingAddress() {
        return addressMapper.toEntity(
                AddressData.builder()
                        .city(DEFAULT_SHIPPING_ADDRESS_CITY)
                        .street(DEFAULT_SHIPPING_ADDRESS_STREET)
                        .postCode(DEFAULT_SHIPPING_ADDRESS_POST_CODE)
                        .position(new GeometryFactory().createPoint(new Coordinate( 0,0)))
                        .build()
        );
    }

    @Override
    public Order save(OrderData orderData)  {
        return orderRepository.save(orderData);
    }

    @Override
    public Order create(OrderData orderData) {
        return orderMapper.toEntity(orderData);
    }

    @Override
    public Order createDefault() {
        return create(defaultData().build());
    }

    @Override
    public Order saveDefault() {
        return save(defaultData().build());
    }

}
