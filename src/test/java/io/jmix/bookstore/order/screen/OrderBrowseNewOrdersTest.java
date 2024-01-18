//package io.jmix.bookstore.order.screen;
//
//import io.jmix.bookstore.customer.Customer;
//import io.jmix.bookstore.customer.test_support.Customers;
//import io.jmix.bookstore.order.entity.Order;
//import io.jmix.bookstore.order.entity.OrderStatus;
//import io.jmix.bookstore.order.test_support.Orders;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.DataGridInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.core.EntityStates;
//import io.jmix.ui.Screens;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class OrderBrowseNewOrdersTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    private Order order;
//    @Autowired
//    private Orders orders;
//    @Autowired
//    private Customers customers;
//    private DataGridInteractions<Order> ordersDataGrid;
//    private ScreenInteractions screenInteractions;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(Customer.class);
//        databaseCleanup.removeAllEntities(Order.class);
//
//        Customer customer = customers.saveDefault();
//        order = orders.save(
//                orders.defaultData()
//                        .customer(customer)
//                        .status(OrderStatus.NEW)
//                        .build()
//        );
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        OrderBrowse orderBrowse = screenInteractions.open(OrderBrowse.class);
//        ordersDataGrid = newOrdersDataGrid(orderBrowse);
//    }
//
//    @Test
//    void given_oneOrderExists_when_openOrderBrowse_then_dataGridContainsTheOrder() {
//
//        // expect:
//        assertThat(ordersDataGrid.firstItem())
//                .isEqualTo(order);
//    }
//
//    @Test
//    void when_createOrder_then_OrderEditScreenIsShown() {
//
//        // given:
//        ordersDataGrid.create();
//
//        // then:
//        OrderEdit orderEdit = screenInteractions.findOpenScreen(OrderEdit.class);
//
//        assertThat(orderEdit)
//                .isNotNull();
//    }
//
//    @Test
//    void given_oneNewOrderExists_when_confirmOrder_then_confirmOrderScreenIsShown() {
//
//        // given:
//        Order firstOrder = ordersDataGrid.firstItem();
//
//        // and:
//        ordersDataGrid.selectFirst();
//
//        ordersDataGrid.click("confirmBtn");
//
//        // then:
//        ConfirmOrder confirmOrder = screenInteractions.findOpenScreen(ConfirmOrder.class);
//
//        assertThat(confirmOrder.getEditedEntity())
//                .isEqualTo(firstOrder);
//    }
//
//    @NotNull
//    private DataGridInteractions<Order> newOrdersDataGrid(OrderBrowse orderBrowse) {
//        return DataGridInteractions.of(orderBrowse, Order.class, "newOrdersTable");
//    }
//}
