package io.jmix.bookstore.view.order;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.directions.CalculatedRoute;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.directions.RouteAccuracy;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderStatus;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Route(value = "confirm-order/:id", layout = MainView.class)
@ViewController("bookstore_Order.confirm")
@ViewDescriptor("confirm-order-view.xml")
@EditedEntityContainer("orderDc")
public class ConfirmOrderView extends StandardDetailView<Order> {

    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Geocoding geocoding;
    @Autowired
    private Dialogs dialogs;

    @ViewComponent
    private TypedTextField<Object> durationField;
    @ViewComponent
    private TypedTextField<Object> distanceField;
    @ViewComponent
    private CollectionContainer<FulfillmentCenter> fulfillmentCentersDc;
    @ViewComponent("fulfilledByMap.shippingAddressLayer.vectorSource")
    private VectorSource shippingAddressVectorSource;

    private Map<FulfillmentCenter, Optional<CalculatedRoute>> calculatedRoutes;
    private LineStringFeature lineStringFeature;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        tryToCalculateRoutesFromFulfillmentCenters();
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        getEditedEntity().setStatus(OrderStatus.CONFIRMED);
    }

    private void tryToCalculateRoutesFromFulfillmentCenters() {
        Address shippingAddress = getEditedEntity().getShippingAddress();
        if (shippingAddress == null || shippingAddress.getPosition() == null) {
            notifications.create(messageBundle.getMessage("noValidShippingAddressFound"))
                    .withType(Notifications.Type.WARNING)
                    .show();
            return;
        }
        calculateRoutesFromFulfillmentCenters(shippingAddress);
    }

    private void calculateRoutesFromFulfillmentCenters(Address shippingAddress) {
        MarkerFeature markerFeature = new MarkerFeature(shippingAddress.getPosition());
        markerFeature.removeAllStyles();
        markerFeature = markerFeature.withStyles(new Style()
                    .withImage(new IconStyle()
                            .withSrc("icons/star.svg")
                            .withScale(2.0)
                            .withColor("#FF0000")));

        shippingAddressVectorSource.addFeature(markerFeature);

        List<FulfillmentCenter> fulfillmentCenters = fulfillmentCentersDc.getItems();
        BackgroundTask<Integer, Map<FulfillmentCenter, Optional<CalculatedRoute>>> task = new CalculateRoutesTask(
                fulfillmentCenters,
                shippingAddress
        );
        dialogs.createBackgroundTaskDialog(task)
                .withHeader(messageBundle.getMessage("calculatingRoutesCaption"))
                .withText(messageBundle.getMessage("calculatingRoutesMessage"))
                .withCancelAllowed(true)
                .withTotal(fulfillmentCenters.size())
                .withShowProgressInPercentage(true)
                .open();
    }

    @Subscribe("fulfilledByField")
    public void onFulfilledByFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<FulfillmentCenter>, FulfillmentCenter> event) {
        calculatedRouteFor(getEditedEntity().getFulfilledBy()).ifPresent(this::showRouteOnMap);
    }

    private Optional<CalculatedRoute> calculatedRouteFor(FulfillmentCenter fulfillmentCenter) {
        return calculatedRoutes == null ?
                Optional.empty() :
                Optional.ofNullable(fulfillmentCenter).flatMap(calculatedRoutes::get);
    }

    private void storeCalculatedRoutes(Map<FulfillmentCenter, Optional<CalculatedRoute>> calculatedRoutes) {
        this.calculatedRoutes = calculatedRoutes;
        selectNearestFulfillmentCenter();
    }

    private void selectNearestFulfillmentCenter() {
        Optional<CalculatedRoute> nearestRoute = calculatedRoutes.values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(CalculatedRoute::distanceInMeters));

        nearestRoute.ifPresent(calculatedRoute -> {
            getEditedEntity().setFulfilledBy(
                    calculatedRoutes.entrySet().stream()
                            .filter(fulfillmentCenterOptionalEntry -> fulfillmentCenterOptionalEntry.getValue().equals(nearestRoute))
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null)
            );
            showRouteOnMap(calculatedRoute);
        });
    }

    private void showRouteOnMap(CalculatedRoute calculatedRoute) {
        if (lineStringFeature != null) {
            shippingAddressVectorSource.removeFeature(lineStringFeature);
        }
        lineStringFeature = new LineStringFeature(calculatedRoute.lineString());
        shippingAddressVectorSource.addFeature(lineStringFeature);

        durationField.setValue(calculatedRoute.duration().prettyPrint());
        distanceField.setValue(calculatedRoute.distance().prettyPrint());
    }

    private class CalculateRoutesTask extends BackgroundTask<Integer, Map<FulfillmentCenter, Optional<CalculatedRoute>>> {
        private final List<FulfillmentCenter> fulfillmentCenters;
        private final Address shippingAddress;

        public CalculateRoutesTask(List<FulfillmentCenter> fulfillmentCenters, Address shippingAddress) {
            super(20, TimeUnit.SECONDS, ConfirmOrderView.this);
            this.fulfillmentCenters = fulfillmentCenters;
            this.shippingAddress = shippingAddress;
        }

        @Override
        public Map<FulfillmentCenter, Optional<CalculatedRoute>> run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            AtomicInteger index = new AtomicInteger();
            return fulfillmentCenters.stream()
                    .collect(Collectors.toMap(
                            fulfillmentCenter -> fulfillmentCenter,
                            fulfillmentCenter -> tryToCalculatedRouteFromFulfillmentCenter(fulfillmentCenter, taskLifeCycle, index.incrementAndGet())
                    ));
        }
        private Optional<CalculatedRoute> tryToCalculatedRouteFromFulfillmentCenter(FulfillmentCenter fulfillmentCenter, TaskLifeCycle<Integer> taskLifeCycle, int i) {
            try {
                taskLifeCycle.publish(i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return geocoding.calculateRoute(
                    fulfillmentCenter.getAddress().getPosition(),
                    shippingAddress.getPosition(),
                    RouteAccuracy.LOW_ACCURACY);
        }

        @Override
        public void done(Map<FulfillmentCenter, Optional<CalculatedRoute>> result) {
            storeCalculatedRoutes(result);
        }
    }
}