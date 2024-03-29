package io.jmix.bookstore.view.fulfillmentcenter;

import io.jmix.bookstore.fulfillment.FulfillmentCenter;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "fulfillmentCenters", layout = MainView.class)
@ViewController("bookstore_FulfillmentCenter.list")
@ViewDescriptor("fulfillment-center-list-view.xml")
@LookupComponent("fulfillmentCentersDataGrid")
@DialogMode(width = "64em")
public class FulfillmentCenterListView extends StandardListView<FulfillmentCenter> {
}