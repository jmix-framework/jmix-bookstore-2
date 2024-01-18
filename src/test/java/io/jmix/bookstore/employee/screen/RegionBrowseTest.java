//package io.jmix.bookstore.employee.screen;
//
//import io.jmix.bookstore.employee.Region;
//import io.jmix.bookstore.employee.Territory;
//import io.jmix.bookstore.employee.region.screen.RegionBrowse;
//import io.jmix.bookstore.employee.region.screen.RegionEdit;
//import io.jmix.bookstore.employee.test_support.Regions;
//import io.jmix.bookstore.fulfillment.FulfillmentCenter;
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
//class RegionBrowseTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//    @Autowired
//    private Regions regions;
//
//    private Region region;
//    private ScreenInteractions screenInteractions;
//    private DataGridInteractions<Region> regionsDataGrid;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//
//        databaseCleanup.removeAllEntities(FulfillmentCenter.class);
//        databaseCleanup.removeAllEntities(Territory.class);
//        databaseCleanup.removeAllEntities(Region.class);
//        region = regions.saveDefault();
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        RegionBrowse regionBrowse = screenInteractions.open(RegionBrowse.class);
//        regionsDataGrid = regionDataGrid(regionBrowse);
//    }
//
//    @Test
//    void given_oneRegionExists_when_openRegionBrowse_then_dataGridContainsTheRegion() {
//
//        // expect:
//        assertThat(regionsDataGrid.firstItem())
//                .isEqualTo(region);
//    }
//
//
//    @Test
//    void given_oneRegionExists_when_editRegion_then_editRegionEditorIsShown() {
//
//        // given:
//        Region firstRegion = regionsDataGrid.firstItem();
//
//        // and:
//        regionsDataGrid.edit(firstRegion);
//
//        // when:
//        var regionEdit = screenInteractions.findOpenScreen(RegionEdit.class);
//
//        // then:
//        assertThat(regionEdit.getEditedEntity())
//                .isEqualTo(firstRegion);
//    }
//
//
//    @NotNull
//    private DataGridInteractions<Region> regionDataGrid(RegionBrowse regionBrowse) {
//        return DataGridInteractions.of(regionBrowse, Region.class, "regionsTable");
//    }
//}
