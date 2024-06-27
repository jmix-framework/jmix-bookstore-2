package io.jmix.bookstore.view.fulfillmentcenter;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import io.jmix.bookstore.directions.AddressInformation;
import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = "fulfillmentCenters/:id", layout = MainView.class)
@ViewController("bookstore_FulfillmentCenter.detail")
@ViewDescriptor("fulfillment-center-detail-view.xml")
@EditedEntityContainer("fulfillmentCenterDc")
public class FulfillmentCenterDetailView extends StandardDetailView<FulfillmentCenter> {

    @ViewComponent
    private GeoMap map;

    @Autowired
    private Geocoding geocoding;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (getEditedEntity().getGeometry() != null) {
            map.zoomToGeometry(getEditedEntity().getGeometry());
        }
    }

    @Subscribe(id = "locationLookupBtn", subject = "clickListener")
    public void onLocationLookupBtnClick(final ClickEvent<JmixButton> event) {
        Address address = getEditedEntity().getAddress();
        Optional<Point> possibleLocation = geocoding
                .forwardGeocoding(new AddressInformation(
                        address.getStreet(),
                        address.getPostCode(),
                        address.getCity(),
                        null,
                        null
                ));

        possibleLocation.ifPresent(it -> {
            address.setPosition(it);
            map.zoomToGeometry(it);
            notifications.create(messageBundle.getMessage("locationLookupPerformed"))
                    .withPosition(Notification.Position.BOTTOM_END)
                    .show();
        });

    }
}