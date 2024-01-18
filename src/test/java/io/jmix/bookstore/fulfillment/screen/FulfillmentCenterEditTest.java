//package io.jmix.bookstore.fulfillment.screen;
//
//import io.jmix.bookstore.employee.Region;
//import io.jmix.bookstore.employee.test_support.Regions;
//import io.jmix.bookstore.entity.Address;
//import io.jmix.bookstore.fulfillment.FulfillmentCenter;
//import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenterData;
//import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenters;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.FormInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.core.querycondition.PropertyCondition;
//import io.jmix.mapsui.component.GeoMap;
//import io.jmix.mapsui.component.layer.VectorLayer;
//import io.jmix.ui.Screens;
//import io.jmix.ui.util.OperationResult;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static io.jmix.bookstore.entity.Assertions.assertThat;
//
//
//class FulfillmentCenterEditTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//    @Autowired
//    private FulfillmentCenters fulfillmentCenters;
//    @Autowired
//    private Regions regions;
//
//    private Region region;
//    private FulfillmentCenterEdit fulfillmentCenterEdit;
//    private ScreenInteractions screenInteractions;
//
//    @BeforeEach
//    void setUp(Screens screens) {
//        // given:
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(FulfillmentCenter.class);
//
//
//        region = regions.saveDefault();
//
//        // and:
//        screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//    }
//
//    @Nested
//    class FormInteraction {
//        FormInteractions formInteractions;
//
//        @BeforeEach
//        void setUp() {
//            fulfillmentCenterEdit = screenInteractions.openEditorForCreation(FulfillmentCenterEdit.class, FulfillmentCenter.class);
//            formInteractions = FormInteractions.of(fulfillmentCenterEdit);
//        }
//
//        @Test
//        void given_validFulfillmentCenter_when_saveFulfillmentCenterThroughTheForm_then_fulfillmentCenterIsSaved() {
//
//            // given:
//            FulfillmentCenterData fulfillmentCenterData = fulfillmentCenters.defaultData().build();
//
//            formInteractions.setTextFieldValue("nameField", fulfillmentCenterData.getName());
//            formInteractions.setTextFieldValue("addressStreetField", fulfillmentCenterData.getAddress().getStreet());
//            formInteractions.setEntityComboBoxFieldValue("regionField", region, Region.class);
//
//            // when:
//            OperationResult operationResult = formInteractions.saveForm();
//
//            assertThat(operationResult)
//                    .isEqualTo(OperationResult.success());
//
//            // then:
//            Optional<FulfillmentCenter> savedFulfillmentCenter = findFulfillmentCenterByAttribute("name", fulfillmentCenterData.getName());
//
//            assertThat(savedFulfillmentCenter)
//                    .isPresent()
//                    .get()
//                    .extracting("region")
//                    .isEqualTo(region);
//
//        }
//
//        @Test
//        void given_fulfillmentCenterWithoutStreet_when_saveFulfillmentCenterThroughTheForm_then_fulfillmentCenterIsNotSaved() {
//
//            // given:
//            FulfillmentCenterData fulfillmentCenterData = fulfillmentCenters.defaultData().build();
//
//            formInteractions.setTextFieldValue("nameField", fulfillmentCenterData.getName());
//
//            // and:
//            String invalidStreetAddress = "";
//            formInteractions.setTextFieldValue("addressStreetField", invalidStreetAddress);
//
//            // when:
//            OperationResult operationResult = formInteractions.saveForm();
//
//            assertThat(operationResult)
//                    .isEqualTo(OperationResult.fail());
//
//            // then:
//            Optional<FulfillmentCenter> savedFulfillmentCenter = findFulfillmentCenterByAttribute("name", fulfillmentCenterData.getName());
//
//            assertThat(savedFulfillmentCenter)
//                    .isNotPresent();
//
//        }
//
//        @NotNull
//        private Optional<FulfillmentCenter> findFulfillmentCenterByAttribute(String attribute, String value) {
//            return dataManager.load(FulfillmentCenter.class)
//                    .condition(PropertyCondition.equal(attribute, value))
//                    .optional();
//        }
//
//    }
//
//    @Nested
//    class MapInteraction {
//
//        private FulfillmentCenter fulfillmentCenter;
//        private Address fulfillmentCenterAddress;
//
//        @BeforeEach
//        void setUp() {
//
//            fulfillmentCenter = fulfillmentCenters.save(
//                    fulfillmentCenters.defaultData()
//                            .region(region)
//                            .build()
//            );
//
//            fulfillmentCenterAddress = fulfillmentCenter.getAddress();
//
//        }
//
//        @Test
//        void given_existingFulfillmentCenter_when_openScreen_then_addressIsDisplayedOnMap() {
//
//            // given:
//            fulfillmentCenterEdit = screenInteractions.openEditorForEditing(FulfillmentCenterEdit.class, FulfillmentCenter.class, fulfillmentCenter);
//
//            // when:
//            GeoMap addressMap = addressMap(fulfillmentCenterEdit);
//            VectorLayer<Address> addressLayer = addressLayer(addressMap);
//
//            // then:
//            assertThat(addressesOnMap(addressLayer))
//                    .hasSize(1);
//
//            // and:
//            assertThat(addressesOnMap(addressLayer).get(0))
//                    .hasPosition(fulfillmentCenterAddress.getPosition());
//
//            // and:
//            assertThat(addressMap.getCenter())
//                    .isEqualTo(fulfillmentCenterAddress.getPosition());
//        }
//
//        private List<Address> addressesOnMap(VectorLayer<Address> addressLayer) {
//            return new ArrayList<>(addressLayer.getGeoObjects());
//        }
//
//        private VectorLayer<Address> addressLayer(GeoMap addressMap) {
//            return addressMap.getLayer("addressLayer");
//        }
//
//        private GeoMap addressMap(FulfillmentCenterEdit fulfillmentCenterEdit1) {
//            return (GeoMap) fulfillmentCenterEdit1.getWindow().getComponentNN("addressMap");
//        }
//
//    }
//
//}
