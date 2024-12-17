package io.jmix.bookstore.view.supplierorder;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.creation.SupplierOrderDraftCreationConfig;
import io.jmix.bookstore.view.main.MainView;
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
    @ViewComponent
    private MessageBundle messageBundle;

    @ViewComponent
    private Div nextCalculationWrapper;
    @ViewComponent
    private Span remainingTime;

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

        remainingTime.setText(timeUntilNextTrigger.prettyPrint());

        if (timeUntilNextTrigger.isHigh()) {
            nextCalculationWrapper.setClassName("gap-s position-red");
        } else if (timeUntilNextTrigger.isMedium()) {
            nextCalculationWrapper.setClassName("gap-s position-yellow");
        } else if (timeUntilNextTrigger.isLow()) {
            nextCalculationWrapper.setClassName("gap-s position-green");
        }
    }

    public LocalDateTime nextTriggerTime() {
        LocalDateTime dateTime = timeSource.now().toLocalDateTime();
        int minutes = dateTime.getMinute();
        int mod = minutes % SupplierOrderDraftCreationConfig.INTERVAL_MIN;

        return dateTime.plusMinutes(SupplierOrderDraftCreationConfig.INTERVAL_MIN - mod)
                .truncatedTo(ChronoUnit.MINUTES);
    }
}