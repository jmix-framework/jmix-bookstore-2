package io.jmix.bookstore.view.customer;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customer-lookup", layout = MainView.class)
@ViewController("bookstore_Customer.lookup")
@ViewDescriptor("customer-lookup-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerLookupView extends StandardListView<Customer> {

    @ViewComponent
    private CollectionLoader<Customer> customersDl;
    @ViewComponent
    private CollectionContainer<Customer> customersDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe("customerFilterField")
    public void onCustomerFilterFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        String searchString = event.getValue();

        customersDl.setParameter("searchString", "%" + searchString + "%");
        customersDl.load();

        if (searchString != null && customersDc.getItems().isEmpty()) {
            notifications.create(messageBundle.formatMessage("noCustomerFoundForSearch", searchString)).show();
        }
    }
}