package io.jmix.bookstore.order.fake;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.product.Product;
import io.jmix.core.DataManager;
import io.jmix.email.impl.EmailerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class OrderFakeGenerator {

    @Autowired
    protected DataManager dataManager;

    public List<Order> generateRandomOrdersWithData(int count) {
        List<Customer> customers = dataManager.load(Customer.class)
                .all()
                .list();
        List<Product> products = dataManager.load(Product.class)
                .all()
                .list();
        List<Address> addresses = dataManager.load(Order.class)
                .all()
                .list()
                .stream()
                .map(Order::getShippingAddress)
                .filter(Objects::nonNull)
                .toList();
        return generateRandomOrders(customers, addresses, products, count);
    }


    public List<Order> generateRandomOrders(List<Customer> customers, List<Address> addresses, List<Product> products, int count) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = customers.get(new Random().nextInt(customers.size()));
            orders.add(generateRandomOrder(customer, addresses, products));
        }
        return orders;
    }

    public Order generateRandomOrder(Customer customer, List<Address> addresses, List<Product> products) {
        Order order = dataManager.create(Order.class);
        order.setOrderNumber(generateRandomOrderNumber());
        order.setCustomer(customer);

        order.setStatus(generateRandomStatus());
        order.setOrderDate(generateRandomDate());

        order.setShippingAddress(addresses.get(new Random().nextInt(addresses.size())));
        if (new Random().nextBoolean()) {
            order.setShippingDate(generateRandomDate());
        }

        List<OrderLine> orderLines = generateRandomOrderLines(order, products);
        order.setOrderLines(orderLines);

        return order;
    }

    private Long generateRandomOrderNumber() {
        return (long) (new Random().nextInt(0, 5000));
    }

    private LocalDate generateRandomDate() {
        int randomDays = new Random().nextInt(2000);
        return LocalDate.now().plusDays(600).minusDays(randomDays);
    }

    private OrderStatus generateRandomStatus() {
        OrderStatus[] statuses = OrderStatus.values();
        return statuses[new Random().nextInt(statuses.length)];
    }

    private Address generateRandomAddress(List<Address> addresses) {
        Address address = dataManager.create(Address.class);
        address.setCity("Random City " + new Random().nextInt(10));
        address.setCountry("Random Country " + new Random().nextInt(10));
        address.setStreet("Random Street " + new Random().nextInt(10));
        address.setState("Random State " + new Random().nextInt(10));
        address.setPostCode("Random PostCode " + new Random().nextInt(10));
        return address;
    }

    private List<OrderLine> generateRandomOrderLines(Order order, List<Product> products) {
        List<OrderLine> orderLines = new ArrayList<>();
        int lineCount = new Random().nextInt(3) + 1;

        for (int i = 0; i < lineCount; i++) {
            Product product = products.get(new Random().nextInt(products.size()));

            OrderLine orderLine = dataManager.create(OrderLine.class);
            orderLine.setOrder(order);
            orderLine.setProduct(product);
            orderLine.setQuantity(new Random().nextInt(10) + 1);
            orderLines.add(orderLine);
        }
        return orderLines;
    }


}
