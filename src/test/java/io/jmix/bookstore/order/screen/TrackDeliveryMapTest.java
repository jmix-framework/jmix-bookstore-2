//package io.jmix.bookstore.order.screen;
//
//import io.jmix.bookstore.entity.Address;
//import io.jmix.bookstore.entity.test_support.Addresses;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.mapsui.component.GeoMap;
//import io.jmix.mapsui.component.layer.VectorLayer;
//import io.jmix.ui.Screens;
//import org.jetbrains.annotations.Nullable;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Collection;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//class TrackDeliveryMapTest extends WebIntegrationTest {
//
//    @Autowired
//    Addresses addresses;
//
//    @Test
//    void given_startAndEndAddressProvided_when_openScreen_then_mapContainsAddressPins(Screens screens) {
//
//        // given:
//        TrackDeliveryMap trackDeliveryMap = screens.create(TrackDeliveryMap.class);
//
//        // and:
//        Address startAddress = addresses.createDefault();
//        trackDeliveryMap.setStart(startAddress);
//        Address endAddress = addresses.createDefault();
//        trackDeliveryMap.setEnd(endAddress);
//
//        // when:
//        trackDeliveryMap.show();
//
//        // then:
//        GeoMap map = map(trackDeliveryMap);
//
//        assertThat(renderedAddresses(map, "startAddressLayer"))
//                .containsExactly(startAddress);
//
//        assertThat(renderedAddresses(map, "endAddressLayer"))
//                .containsExactly(endAddress);
//    }
//
//    private static Collection<Address> renderedAddresses(GeoMap map, String layerId) {
//        VectorLayer<Address> addressLayer = map.getLayer(layerId);
//        return (Collection<Address>) addressLayer.getGeoObjects();
//    }
//
//    @Nullable
//    private GeoMap map(TrackDeliveryMap trackDeliveryMap) {
//        return (GeoMap) trackDeliveryMap.getWindow().getComponent("map");
//    }
//}
