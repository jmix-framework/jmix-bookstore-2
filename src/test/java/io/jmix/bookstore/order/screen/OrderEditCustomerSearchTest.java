//package io.jmix.bookstore.order.screen;
//
//import io.jmix.bookstore.customer.Customer;
//import io.jmix.bookstore.order.entity.Order;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.customer.test_support.Customers;
//import io.jmix.bookstore.order.test_support.Orders;
//import io.jmix.bookstore.test_support.ui.FormInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.ui.Screens;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//import static io.jmix.bookstore.entity.Assertions.assertThat;
//
//
//class OrderEditCustomerSearchTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    @Autowired
//    private Customers customers;
//    @Autowired
//    private Orders orders;
//
//    FormInteractions formInteractions;
//    private Customer mrMiyagi;
//    private Customer lordVoldemort;
//    private Customer lukeSkywalker;
//
//
//    @BeforeEach
//    void setUp() {
//
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(Customer.class);
//
//
//        mrMiyagi = customers.save(
//                customers.defaultData()
//                        .firstName("Mr")
//                        .lastName("Miyagi")
//                        .build()
//        );
//
//        lordVoldemort = customers.save(
//                customers.defaultData()
//                        .firstName("Lord")
//                        .lastName("Voldemort")
//                        .build()
//        );
//
//        lukeSkywalker = customers.save(
//                customers.defaultData()
//                        .firstName("Luke")
//                        .lastName("Skywalker")
//                        .build()
//        );
//    }
//
//    @Test
//    void given_twoCustomersWithLetterLArePresent_when_searchingForLetterL_then_twoCustomersAreFound(Screens screens) {
//
//        // given:
//        OrderEdit orderEdit = openOrderEditor(screens);
//
//        formInteractions = FormInteractions.of(orderEdit);
//
//        // and:
//        List<Customer> searchResults = formInteractions.getSuggestions("L", "customerField", Customer.class);
//
//        assertThat(searchResults)
//                .containsExactlyInAnyOrder(lordVoldemort, lukeSkywalker);
//
//    }
//
//    @Test
//    void given_aMatchingCustomer_when_selectingTheCustomer_then_theCustomerIsAssignedToTheOrder(Screens screens) {
//
//        // given:
//        OrderEdit orderEdit = openOrderEditor(screens);
//
//        formInteractions = FormInteractions.of(orderEdit);
//
//        // and:
//        List<Customer> searchResults = formInteractions.getSuggestions("Mr", "customerField", Customer.class);
//
//        assertThat(searchResults)
//                .containsExactlyInAnyOrder(mrMiyagi);
//
//        // when:
//        formInteractions.setEntitySuggestionFieldValue("customerField", mrMiyagi, Customer.class);
//
//        // and:
//        assertThat(orderEdit.getEditedEntity())
//                .hasCustomer(mrMiyagi);
//    }
//
//    @Test
//    void given_aMatchingCustomer_when_selectingTheCustomer_then_theCustomerDetailsAreDisplayed(Screens screens) {
//
//        // given:
//        OrderEdit orderEdit = openOrderEditor(screens);
//
//        formInteractions = FormInteractions.of(orderEdit);
//
//        // and:
//        List<Customer> searchResults = formInteractions.getSuggestions("Mr", "customerField", Customer.class);
//
//        assertThat(searchResults)
//                .containsExactlyInAnyOrder(mrMiyagi);
//
//        // when:
//        formInteractions.setEntitySuggestionFieldValue("customerField", mrMiyagi, Customer.class);
//
//        // and:
//        assertThat(formInteractions.isVisible("customerDisplayField"))
//                .isTrue();
//        assertThat(formInteractions.isVisible("customerField"))
//                .isFalse();
//
//        assertThat(formInteractions.getLabelValue("customerDisplayField"))
//                .isEqualTo("Mr Miyagi");
//    }
//
//    @Test
//    void given_existingOrderWithAssociatedCustomer_when_openTheScreen_then_theCustomerDetailsAreDisplayed(Screens screens) {
//
//        // given:
//        Order order = orders.save(
//                orders.defaultData()
//                        .customer(mrMiyagi)
//                        .build()
//        );
//
//        // when:
//        OrderEdit orderEdit = openOrderEditor(screens, order);
//
//        formInteractions = FormInteractions.of(orderEdit);
//
//        // and:
//        assertThat(formInteractions.isVisible("customerDisplayField"))
//                .isTrue();
//
//        assertThat(formInteractions.getLabelValue("customerDisplayField"))
//                .isEqualTo("Mr Miyagi");
//    }
//
//    @Test
//    void given_existingOrderWithAssociatedCustomer_when_clearCustomerSelection_then_theCustomerSearchIsDisplayed(Screens screens) {
//
//        // given:
//        Order order = orders.save(
//                orders.defaultData()
//                        .customer(mrMiyagi)
//                        .build()
//        );
//
//        // and:
//        OrderEdit orderEdit = openOrderEditor(screens, order);
//
//        formInteractions = FormInteractions.of(orderEdit);
//
//        // when:
//        formInteractions.click("clearCustomerBtn");
//
//        // and:
//        assertThat(formInteractions.isVisible("customerDisplayField"))
//                .isFalse();
//        assertThat(formInteractions.isVisible("customerField"))
//                .isTrue();
//
//        assertThat(orderEdit.getEditedEntity())
//                .hasCustomer(null);
//    }
//
//    private OrderEdit openOrderEditor(Screens screens, Order order) {
//        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//        return screenInteractions.openEditorForEditing(OrderEdit.class, Order.class, order);
//    }
//
//    private OrderEdit openOrderEditor(Screens screens) {
//        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//        return screenInteractions.openEditorForCreation(OrderEdit.class, Order.class);
//    }
//
//}
