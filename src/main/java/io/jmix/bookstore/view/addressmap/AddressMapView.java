package io.jmix.bookstore.view.addressmap;


import com.vaadin.flow.router.Route;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route(value = "address-map-view", layout = MainView.class)
@ViewController("bookstore_AddressMapView")
@ViewDescriptor("address-map-view.xml")
public class AddressMapView extends StandardView {

    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @ViewComponent
    private GeoMap map;
    @ViewComponent("map.addressLayer.addressSource")
    private VectorSource addressSource;

    private Address address;

    public void setAddress(Address address) {
        this.address = address;
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        bindAddressToMap();
    }

    private void bindAddressToMap() {
        if (address.getPosition() == null) {
            notifications.show(messageBundle.getMessage("addressNotFound"));
            return;
        }
        addressSource.addFeature(new MarkerFeature(address.getPosition()));
        map.zoomToGeometry(address.getPosition());
    }

    @Override
    public String getPageTitle() {
        if (address == null) {
            return super.getPageTitle();
        } else {
            String addressString = Stream.of(
                            address.getStreet(),
                            address.getCity(),
                            address.getPostCode()
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));

            return messageBundle.formatMessage("AddressMapView.title", addressString);
        }
    }
}