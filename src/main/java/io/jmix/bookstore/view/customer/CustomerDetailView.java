package io.jmix.bookstore.view.customer;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bookstore.customer.Customer;

import io.jmix.bookstore.view.addressmap.AddressMapView;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers/:id", layout = MainView.class)
@ViewController("bookstore_Customer.detail")
@ViewDescriptor("customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {

    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe(id = "showOnMapBtn", subject = "clickListener")
    public void onShowOnMapBtnClick(final ClickEvent<JmixButton> event) {
        DialogWindow<AddressMapView> window = dialogWindows.view(this, AddressMapView.class).build();
        window.getView().setAddress(getEditedEntity().getAddress());
        window.open();
    }
}