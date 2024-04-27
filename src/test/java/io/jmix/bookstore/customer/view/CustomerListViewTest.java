package io.jmix.bookstore.customer.view;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.test_support.ui.DataGridInteractions;
import io.jmix.bookstore.test_support.ui.ViewInteractions;
import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
import io.jmix.bookstore.view.customer.CustomerDetailView;
import io.jmix.bookstore.view.customer.CustomerListView;
import io.jmix.flowui.ViewNavigators;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerListViewTest extends WebIntegrationTest {

    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private Customers customers;

    private ViewInteractions viewInteractions;

    private Customer customer;
    private DataGridInteractions<Customer> customerDataGrid;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities();
        databaseCleanup.removeAllEntities(Customer.class);
        customer = customers.saveDefault();

        viewInteractions = ViewInteractions.forNavigation(viewNavigators);
        CustomerListView customerListView = viewInteractions.navigate(CustomerListView.class);

        customerDataGrid = customerDataGrid(customerListView);
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_dataGridContainsTheCustomer() {
        // expect:
        assertThat(customerDataGrid.firstItem())
                .isEqualTo(customer);
    }


    @Test
    void given_oneCustomerExists_when_editCustomer_then_editCustomerEditorIsShown() {
        // given:
        Customer firstCustomer = customerDataGrid.firstItem();

        // and:
        customerDataGrid.edit(firstCustomer);

        // then:
        CustomerDetailView customerDetailView = viewInteractions.findOpenView(CustomerDetailView.class);

        assertThat(customerDetailView.getEditedEntity())
                .isEqualTo(firstCustomer);
    }

    @NotNull
    private DataGridInteractions<Customer> customerDataGrid(CustomerListView customerListView) {
        return DataGridInteractions.of(customerListView, Customer.class, "customersDataGrid");
    }
}
