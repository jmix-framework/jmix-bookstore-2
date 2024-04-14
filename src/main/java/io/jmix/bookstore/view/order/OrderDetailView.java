package io.jmix.bookstore.view.order;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.MetadataTools;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

@Route(value = "orders/:id", layout = MainView.class)
@ViewController("bookstore_Order.detail")
@ViewDescriptor("order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {

    @ViewComponent
    private H4 customerDisplayField;
    @ViewComponent
    private Div customerAddressField;
    @ViewComponent
    private VerticalLayout customerDetailsBox;
    @ViewComponent
    private EntityComboBox<Customer> customerField;

    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private TimeSource timeSource;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Order> event) {
        event.getEntity().setStatus(OrderStatus.NEW);
        event.getEntity().setOrderDate(timeSource.now().toLocalDate());

        customerDetailsBox.setVisible(false);
        displayCustomer(null);

        customerField.focus();
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        displayCustomer(getEditedEntity().getCustomer());
    }

    @Subscribe("customerField")
    public void onCustomerFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Customer>, Customer> event) {
        customerField.setVisible(false);
        customerDetailsBox.setVisible(true);
        Customer customer = event.getValue();
        displayCustomer(customer);
    }

    @Nullable
    private void displayCustomer(Customer customer) {
        if (customer == null) {
            customerDisplayField.setText("");
            customerAddressField.setText("");
        } else {
            customerDisplayField.setText(metadataTools.getInstanceName(customer));
            customerAddressField.setText(metadataTools.getInstanceName(customer.getAddress()));
        }
    }

    @Subscribe(id = "clearCustomerBtn", subject = "clickListener")
    public void onClearCustomerBtnClick(final ClickEvent<JmixButton> event) {
        getEditedEntity().setCustomer(null);
        customerField.setVisible(true);
        customerDetailsBox.setVisible(false);
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        getEditedEntity().setShippingAddress(getEditedEntity().getCustomer().getAddress());
    }
}