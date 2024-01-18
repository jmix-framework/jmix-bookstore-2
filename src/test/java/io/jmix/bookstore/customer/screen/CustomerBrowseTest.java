//package io.jmix.bookstore.customer.screen;
//
//import io.jmix.bookstore.customer.Customer;
//import io.jmix.bookstore.customer.test_support.Customers;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.DataGridInteractions;
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
//class CustomerBrowseTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    private Customer customer;
//    @Autowired
//    private Customers customers;
//    private DataGridInteractions<Customer> customerDataGrid;
//    private ScreenInteractions screenInteractions;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(Customer.class);
//        customer = customers.saveDefault();
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);
//        customerDataGrid = customerDataGrid(customerBrowse);
//    }
//
//    @Test
//    void given_oneCustomerExists_when_openCustomerBrowse_then_dataGridContainsTheCustomer() {
//
//        // expect:
//        assertThat(customerDataGrid.firstItem())
//                .isEqualTo(customer);
//    }
//
//
//    @Test
//    void given_oneCustomerExists_when_editCustomer_then_editCustomerEditorIsShown() {
//
//        // given:
//        Customer firstCustomer = customerDataGrid.firstItem();
//
//        // and:
//        customerDataGrid.edit(firstCustomer);
//
//        // then:
//        CustomerEdit customerEdit = screenInteractions.findOpenScreen(CustomerEdit.class);
//
//        assertThat(customerEdit.getEditedEntity())
//                .isEqualTo(firstCustomer);
//    }
//
//    @NotNull
//    private DataGridInteractions<Customer> customerDataGrid(CustomerBrowse customerBrowse) {
//        return DataGridInteractions.of(customerBrowse, Customer.class, "customersTable");
//    }
//}
