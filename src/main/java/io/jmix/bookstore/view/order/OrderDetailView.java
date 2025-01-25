package io.jmix.bookstore.view.order;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.core.SaveContext;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.Set;

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
    private MeterRegistry meterRegistry;

    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private EntityPicker<FulfillmentCenter> fulfilledByField;
    @ViewComponent
    private TypedTextField<Long> orderNumberField;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Order> event) {
        event.getEntity().setStatus(OrderStatus.NEW);
        event.getEntity().setOrderDate(timeSource.now().toLocalDate());

        orderNumberField.setVisible(false);
        fulfilledByField.setVisible(false);
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

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return dataManager.save(saveContext);
        } finally {
            Timer timer = Timer.builder("jmix_bookstore_order_save_time")
                    .publishPercentiles(0.5, 0.75, 0.95)
                    .register(meterRegistry);
            sample.stop(timer);
        }

    }
}