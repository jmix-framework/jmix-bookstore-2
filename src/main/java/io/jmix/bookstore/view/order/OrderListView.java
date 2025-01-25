package io.jmix.bookstore.view.order;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.AccessManager;
import io.jmix.core.Messages;
import io.jmix.core.TimeSource;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.accesscontext.UiShowViewContext;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import static io.jmix.bookstore.JmixBookstoreApplication.PERFORMANCE_TESTS_PROFILE;

@Route(value = "orders", layout = MainView.class)
@ViewController("bookstore_Order.list")
@ViewDescriptor("order-list-view.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView extends StandardListView<Order> {

    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Messages messages;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private AccessManager accessManager;
    @Autowired
    private Environment environment;
    @Autowired
    private TimeSource timeSource;

    @ViewComponent
    private DataGrid<Order> ordersDataGrid;
    @ViewComponent
    private DataGrid<Order> confirmedOrdersDataGrid;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionLoader<Order> confirmedOrdersDl;
    @ViewComponent
    private CollectionLoader<Order> finishedOrdersDl;
    @ViewComponent
    private JmixTabSheet orderListTabSheet;


    @Subscribe
    public void onInit(final InitEvent event) {
        if (Arrays.asList(environment.getActiveProfiles()).contains(PERFORMANCE_TESTS_PROFILE)) {//fix id absence for tests
            orderListTabSheet.getElement().getChildren().filter(elm -> "vaadin-tabs".equals(elm.getTag())).findFirst().ifPresent(
                    elm -> elm.setAttribute("id", "orderListVaadinTabs")
            );
        }
    }


    @Subscribe("ordersDataGrid.confirm")
    public void onOrdersDataGridConfirm(final ActionPerformedEvent event) {
        viewNavigators.detailView(ordersDataGrid)
                .withViewClass(ConfirmOrderView.class)
                .navigate();
    }

    @Subscribe("confirmedOrdersDataGrid.markAsInDelivery")
    public void onConfirmedOrdersDataGridMarkAsInDelivery(final ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("confirmMarkAsInDeliveryCaption"))
                .withContent(new Html(messageBundle.getMessage("confirmMarkAsInDeliveryMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentOrderAsInDelivery()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    @Subscribe("confirmedOrdersDataGrid.markAsDelivered")
    public void onConfirmedOrdersDataGridMarkAsDelivered(final ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("confirmMarkAsDeliveredCaption"))
                .withContent(new Html(messageBundle.getMessage("confirmMarkAsDeliveredMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentOrderAsDelivered()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    @Subscribe("confirmedOrdersDataGrid.trackDelivery")
    public void onConfirmedOrdersDataGridTrackDelivery(final ActionPerformedEvent event) {
        Order orderToTrack = confirmedOrdersDataGrid.getSingleSelectedItem();
        if (orderToTrack != null) {
            if (!orderToTrack.getStatus().equals(OrderStatus.IN_DELIVERY)) {
                notifications.create(messageBundle.formatMessage("trackDeliveryInvalidStatus", messages.getMessage(OrderStatus.IN_DELIVERY)))
                        .withType(Notifications.Type.ERROR)
                        .show();
            } else {
                DialogWindow<TrackDeliveryMapView> dialogWindow = dialogWindows.view(this, TrackDeliveryMapView.class).build();
                TrackDeliveryMapView trackDeliveryMapView = dialogWindow.getView();
                trackDeliveryMapView.setStart(orderToTrack.getFulfilledBy().getAddress());
                trackDeliveryMapView.setEnd(orderToTrack.getCustomer().getAddress());
                dialogWindow.open();
            }
        }
    }

    private void markCurrentOrderAsInDelivery() {
        Order orderToMarkAsInDelivery = confirmedOrdersDataGrid.getSingleSelectedItem();
        if (orderToMarkAsInDelivery != null) {
            orderToMarkAsInDelivery.setStatus(OrderStatus.IN_DELIVERY);
            orderToMarkAsInDelivery.setShippingDate(timeSource.now().toLocalDate());
            dataContext.save();
            notifications.create(messageBundle.getMessage("orderMarkedAsInDelivery")).show();
            confirmedOrdersDl.load();
            finishedOrdersDl.load();
        }
    }

    private void markCurrentOrderAsDelivered() {
        Order orderToMarkAsDelivered = confirmedOrdersDataGrid.getSingleSelectedItem();
        if (orderToMarkAsDelivered != null) {
            orderToMarkAsDelivered.setStatus(OrderStatus.DELIVERED);
            dataContext.save();
            notifications.create(messageBundle.getMessage("orderMarkedAsDelivered")).show();
            confirmedOrdersDl.load();
            finishedOrdersDl.load();
        }
    }

    @Install(to = "ordersDataGrid.confirm", subject = "enabledRule")
    private boolean ordersDataGridConfirmEnabledRule() {
        UiShowViewContext accessContext = new UiShowViewContext("bookstore_Order.confirm");
        accessManager.applyRegisteredConstraints(accessContext);
        return accessContext.isPermitted();
    }
}