//package io.jmix.bookstore.fulfillment.screen;
//
//import io.jmix.bookstore.employee.Region;
//import io.jmix.bookstore.employee.test_support.Regions;
//import io.jmix.bookstore.fulfillment.FulfillmentCenter;
//import io.jmix.bookstore.fulfillment.test_support.FulfillmentCenters;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.DataGridInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.ui.Screens;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static io.jmix.bookstore.entity.Assertions.assertThat;
//
//class FulfillmentCenterBrowseTest extends WebIntegrationTest {
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
//    private FulfillmentCenter fulfillmentCenter;
//    private ScreenInteractions screenInteractions;
//    private DataGridInteractions<FulfillmentCenter> fulfillmentCentersDataGrid;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(FulfillmentCenter.class);
//
//        Region region = regions.saveDefault();
//        fulfillmentCenter = fulfillmentCenters.save(
//                fulfillmentCenters.defaultData()
//                        .region(region)
//                        .build()
//        );
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        FulfillmentCenterBrowse fulfillmentCenterBrowse = screenInteractions.open(FulfillmentCenterBrowse.class);
//        fulfillmentCentersDataGrid = fulfillmentCenterDataGrid(fulfillmentCenterBrowse);
//    }
//
//    @Test
//    void given_oneFulfillmentCenterExists_when_openFulfillmentCenterBrowse_then_dataGridContainsTheFulfillmentCenter() {
//
//        // expect:
//        assertThat(fulfillmentCentersDataGrid.firstItem())
//                .isEqualTo(fulfillmentCenter);
//    }
//
//
//    @Test
//    void given_oneFulfillmentCenterExists_when_editFulfillmentCenter_then_editFulfillmentCenterEditorIsShown() {
//
//        // given:
//        FulfillmentCenter firstFulfillmentCenter = fulfillmentCentersDataGrid.firstItem();
//
//        // and:
//        fulfillmentCentersDataGrid.edit(firstFulfillmentCenter);
//
//        // when:
//        var fulfillmentCenterEdit = screenInteractions.findOpenScreen(FulfillmentCenterEdit.class);
//
//        // then:
//        assertThat(fulfillmentCenterEdit.getEditedEntity())
//                .isEqualTo(firstFulfillmentCenter);
//    }
//
//
//    @NotNull
//    private DataGridInteractions<FulfillmentCenter> fulfillmentCenterDataGrid(FulfillmentCenterBrowse fulfillmentCenterBrowse) {
//        return DataGridInteractions.of(fulfillmentCenterBrowse, FulfillmentCenter.class, "fulfillmentCentersTable");
//    }
//}
