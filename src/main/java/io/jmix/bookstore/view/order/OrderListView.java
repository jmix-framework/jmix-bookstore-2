package io.jmix.bookstore.view.order;

import io.jmix.bookstore.order.entity.Order;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;

@Route(value = "orders", layout = MainView.class)
@ViewController("bookstore_Order.list")
@ViewDescriptor("order-list-view.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView extends StandardListView<Order> {
}