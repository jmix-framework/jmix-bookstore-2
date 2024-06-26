package io.jmix.bookstore.view.order;


import io.jmix.bookstore.directions.Geocoding;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.FitOptions;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.Padding;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.Anchor;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Route(value = "track-delivery-map", layout = MainView.class)
@ViewController("bookstore_TrackDeliveryMapView")
@ViewDescriptor("track-delivery-map-view.xml")
public class TrackDeliveryMapView extends StandardView {

    @ViewComponent("map.shippingAddressLayer.vectorSource")
    private VectorSource vectorSource;

    private Address startAddress;
    private Address endAddress;
    @Autowired
    private Geocoding geocoding;
    @ViewComponent
    private GeoMap map;

    public void setStart(Address start) {
        this.startAddress = start;
    }

    public void setEnd(Address end) {
        this.endAddress = end;
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        showRouteOnMap();
    }

    private void showRouteOnMap() {
        MarkerFeature startFeature = new MarkerFeature(startAddress.getPosition());
        startFeature.removeAllStyles();
        startFeature = startFeature.withStyles(new Style()
                .withImage(new IconStyle()
                        .withAnchor(new Anchor(0.5, 0.90))
                        .withSrc("icons/map-building-green.svg")
                        .withScale(0.05)));

        vectorSource.addFeature(startFeature);

        MarkerFeature endFeature = new MarkerFeature(endAddress.getPosition());
        endFeature.removeAllStyles();
        endFeature = endFeature.withStyles(new Style()
                .withImage(new IconStyle()
                        .withAnchor(new Anchor(0.5, 0.90))
                        .withSrc("icons/map-user.svg")
                        .withScale(0.05)));

        vectorSource.addFeature(endFeature);

        geocoding.calculateRoute(startAddress.getPosition(), endAddress.getPosition())
                .ifPresent(route -> {
                    LineStringFeature lineStringFeature = new LineStringFeature(route.lineString())
                            .withStyles(new Style()
                                    .withStroke(new Stroke()
                                            .withColor("#2C93E2")
                                            .withWidth(4d)));
                    vectorSource.addFeature(lineStringFeature);

                    Point truckPosition = route.lineString().getPointN(new Random().nextInt(route.lineString().getNumPoints()));
                    MarkerFeature truckFeature = new MarkerFeature(truckPosition);
                    truckFeature.removeAllStyles();
                    truckFeature = truckFeature.withStyles(new Style()
                            .withImage(new IconStyle()
                                    .withAnchor(new Anchor(0.5, 0.90))
                                    .withSrc("icons/map-truck.svg")
                                    .withScale(0.05)));
                    vectorSource.addFeature(truckFeature);

                    map.fit(new FitOptions(lineStringFeature)
                            .withPadding(new Padding(40, 40, 40, 40)));
                });
    }
}