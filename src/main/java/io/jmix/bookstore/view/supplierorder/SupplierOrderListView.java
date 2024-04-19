package io.jmix.bookstore.view.supplierorder;

import com.vaadin.flow.component.html.Div;
import io.jmix.bookstore.product.supplier.SupplierOrder;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Route(value = "supplierOrders", layout = MainView.class)
@ViewController("bookstore_SupplierOrder.list")
@ViewDescriptor("supplier-order-list-view.xml")
@LookupComponent("supplierOrdersDataGrid")
@DialogMode(width = "64em")
public class SupplierOrderListView extends StandardListView<SupplierOrder> {

    @Autowired
    private TimeSource timeSource;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @ViewComponent
    private Div nextCalculationLabel;

    private LocalDateTime nextTriggerTime;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        nextTriggerTime = nextTriggerTime();
        refreshNextCalculationInLabel();
    }

    @Subscribe("remainingTimeTimer")
    public void onRemainingTimeTimerTimerAction(final Timer.TimerActionEvent event) {
        refreshNextCalculationInLabel();
    }

    private void refreshNextCalculationInLabel() {
        LocalDateTime now = timeSource.now().toLocalDateTime();

        WaitingTime timeUntilNextTrigger = WaitingTime.fromNow(now, nextTriggerTime);

        if (now.isAfter(nextTriggerTime)) {
            getViewData().loadAll();
            notifications.create(messageBundle.getMessage("supplierOrdersRefreshed"))
                    .withType(Notifications.Type.DEFAULT)
                    .show();

            nextTriggerTime = nextTriggerTime();
        }

//        if (timeUntilNextTrigger.isHigh()) {
//            nextCalculationLabelHBox.setStyleName("position-badge position-red");
//        }
//        else if (timeUntilNextTrigger.isMedium()) {
//            nextCalculationLabelHBox.setStyleName("position-badge position-yellow");
//        }
//        else if (timeUntilNextTrigger.isLow()) {
//            nextCalculationLabelHBox.setStyleName("position-badge position-green");
//        }

        nextCalculationLabel.setText(messageBundle.formatMessage("nextCreationIn", timeUntilNextTrigger.prettyPrint()));
    }

    public LocalDateTime nextTriggerTime() {
        LocalDateTime dateTime = timeSource.now().toLocalDateTime();
        int minutes = dateTime.getMinute();
        int mod = minutes % 5;

        return dateTime.plusMinutes(5 - mod)
                .truncatedTo(ChronoUnit.MINUTES);
    }
}