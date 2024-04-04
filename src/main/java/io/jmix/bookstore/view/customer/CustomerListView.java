package io.jmix.bookstore.view.customer;

import io.jmix.bookstore.customer.Customer;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "customers", layout = MainView.class)
@ViewController("bookstore_Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {
}