package io.jmix.bookstore.order.event;


import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.order.test_support.OrderLines;
import io.jmix.bookstore.order.test_support.Orders;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.test_data.data_provider.employee.EmployeePositions;
import io.jmix.bookstore.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.maps.utils.GeometryUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static io.jmix.bookstore.entity.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
@ExtendWith(AuthenticatedAsAdmin.class)
@RecordApplicationEvents
public class OrderEventsTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Customers customers;
    @Autowired
    Products products;
    @Autowired
    Orders orders;
    @Autowired
    OrderLines orderLines;
    @Autowired
    ApplicationEvents applicationEvents;
    @Autowired
    Metadata metadata;

    private final LocalDate TODAY = LocalDate.now();
    private Customer customer;
    private Position position;
    private Employee employee;

    @BeforeEach
    void setUp() {

        // given
        customer = customers.saveDefault();

        position = metadata.create(Position.class);
        position.setCode(EmployeePositions.AvailablePosition.SALES_REPRESENTATIVE.getCode());
        position.setName("Sales Representative");

        Address address = dataManager.create(Address.class);
        address.setPosition(GeometryUtils.createPoint(25.0, 39.0));

        employee = metadata.create(Employee.class);
        employee.setFirstName("Lois");
        employee.setLastName("Marsh");
        employee.setBirthDate(LocalDate.of(2000, 1, 1));
        employee.setHireDate(LocalDate.of(2024, 1, 1));
        employee.setAddress(address);
        employee.setPosition(position);

        dataManager.save(position, employee);
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(customer, employee, position);
    }

    @Test
    void given_newOrder_when_confirmOrder_then_orderConfirmedEventIsSent() {

        // given
        Order newOrder = orders.save(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(TODAY)
                        .status(OrderStatus.NEW)
                        .build()
        );

        // when
        newOrder.setStatus(OrderStatus.CONFIRMED);

        // and
        Order confirmedOrder = dataManager.save(newOrder);

        // then
        assertThat(publishedEvents(OrderConfirmedEvent.class))
                .isNotEmpty()
                .first()
                .extracting(OrderConfirmedEvent::order)
                .isEqualTo(confirmedOrder)
                .extracting(Order::getStatus)
                .isEqualTo(OrderStatus.CONFIRMED);
    }

    @NotNull
    private <T> List<T> publishedEvents(Class<T> type) {
        return applicationEvents.stream(type).collect(Collectors.toList());
    }
}
