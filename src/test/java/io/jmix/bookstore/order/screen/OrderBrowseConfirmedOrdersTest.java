//package io.jmix.bookstore.order.screen;
//
//import io.jmix.bookstore.customer.Customer;
//import io.jmix.bookstore.customer.test_support.Customers;
//import io.jmix.bookstore.employee.test_support.Regions;
//import io.jmix.bookstore.fulfillment.FulfillmentCenter;
//import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenters;
//import io.jmix.bookstore.order.entity.Order;
//import io.jmix.bookstore.order.entity.OrderStatus;
//import io.jmix.bookstore.order.test_support.Orders;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.DataGridInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.ui.Screens;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class OrderBrowseConfirmedOrdersTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    private Order confirmedOrder;
//    private Order inDeliveryOrder;
//    private Order newOrder;
//    @Autowired
//    private Orders orders;
//    @Autowired
//    private Regions regions;
//    @Autowired
//    private Customers customers;
//    @Autowired
//    private FulfillmentCenters fulfillmentCenters;
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
//        newOrder = orders.save(
//                orders.defaultData()
//                        .customer(customer)
//                        .status(OrderStatus.NEW)
//                        .build()
//        );
//        confirmedOrder = orders.save(
//                orders.defaultData()
//                        .customer(customer)
//                        .status(OrderStatus.CONFIRMED)
//                        .build()
//        );
//
//        FulfillmentCenter fulfillmentCenter = fulfillmentCenters.save(
//                fulfillmentCenters.defaultData()
//                        .region(regions.saveDefault())
//                        .build()
//        );
//
//        inDeliveryOrder = orders.save(
//                orders.defaultData()
//                        .customer(customer)
//                        .status(OrderStatus.IN_DELIVERY)
//                        .fulfilledBy(fulfillmentCenter)
//                        .build()
//        );
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        OrderBrowse orderBrowse = screenInteractions.open(OrderBrowse.class);
//        ordersDataGrid = confirmedOrdersDataGrid(orderBrowse);
//    }
//
//    @Test
//    void given_oneNewOrderExists_and_oneConfirmedOrderExists_when_openOrderBrowse_then_dataGridContainsTheConfirmedOrder() {
//
//        // expect:
//        assertThat(ordersDataGrid.items())
//                .contains(inDeliveryOrder, confirmedOrder)
//                .doesNotContain(newOrder);
//    }
//
//    @Test
//    void when_editOrder_then_OrderEditScreenIsShown() {
//
//        // given:
//        Order confirmedOrderFromTable = ordersDataGrid.firstItem();
//
//        ordersDataGrid.edit(confirmedOrderFromTable);
//
//        // then:
//        OrderEdit orderEdit = screenInteractions.findOpenScreen(OrderEdit.class);
//
//        assertThat(orderEdit.getEditedEntity())
//                .isEqualTo(confirmedOrderFromTable);
//    }
//
//    @Test
//    void when_trackDelivery_then_TrackDeliveryScreenIsShown() {
//
//        // given:
//        ordersDataGrid.select(inDeliveryOrder);
//
//        ordersDataGrid.click("trackDeliveryBtn");
//
//        // then:
//        TrackDeliveryMap trackDeliveryMap = screenInteractions.findOpenScreen(TrackDeliveryMap.class);
//
//        assertThat(trackDeliveryMap)
//                .isNotNull();
//    }
//
//    @NotNull
//    private DataGridInteractions<Order> confirmedOrdersDataGrid(OrderBrowse orderBrowse) {
//        return DataGridInteractions.of(orderBrowse, Order.class, "confirmedOrdersTable");
//    }
//}
