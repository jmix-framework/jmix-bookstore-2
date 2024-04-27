package io.jmix.bookstore.customer.view;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.customer.test_support.CustomerData;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.test_support.ui.FormInteractions;
import io.jmix.bookstore.test_support.ui.ViewInteractions;
import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
import io.jmix.bookstore.view.customer.CustomerDetailView;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDetailViewTest extends WebIntegrationTest {

    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    private Customers customers;

    FormInteractions formInteractions;

    @BeforeEach
    void setUp() {
        // given:
        databaseCleanup.removeAllEntities();
        databaseCleanup.removeAllEntities(Customer.class);

        // and:
        viewNavigators.view(MainView.class).navigate();
        ViewInteractions viewInteractions = ViewInteractions.forDialog(dialogWindows);
        CustomerDetailView customerEdit = viewInteractions.openDetailForCreation(CustomerDetailView.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);
    }

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved() {

        // given:
        CustomerData customerData = customers.defaultData().build();

        formInteractions.setTextFieldValue("firstNameField", customerData.getFirstName());
        formInteractions.setTextFieldValue("lastNameField", customerData.getLastName());
        formInteractions.setTextFieldValue("addressStreetField", customerData.getAddress().getStreet());

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", customerData.getFirstName());

        assertThat(savedCustomer)
                .isPresent()
                .get()
                .extracting("lastName")
                .isEqualTo(customerData.getLastName());

    }

    @Test
    void given_customerWithoutStreet_when_saveCustomerThroughTheForm_then_customerIsNotSaved() {

        // given:
        CustomerData customerData = customers.defaultData().build();

        formInteractions.setTextFieldValue("firstNameField", customerData.getFirstName());

        // and:
        String invalidStreetAddress = "";
        formInteractions.setTextFieldValue("addressStreetField", invalidStreetAddress);

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.fail());

        // then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", customerData.getFirstName());

        assertThat(savedCustomer)
                .isNotPresent();

    }

    @NotNull
    private Optional<Customer> findCustomerByAttribute(String attribute, String value) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

}
