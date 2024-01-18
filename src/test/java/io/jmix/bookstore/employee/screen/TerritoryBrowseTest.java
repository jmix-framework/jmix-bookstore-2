//package io.jmix.bookstore.employee.screen;
//
//import io.jmix.bookstore.employee.Region;
//import io.jmix.bookstore.employee.Territory;
//import io.jmix.bookstore.employee.territory.screen.TerritoryBrowse;
//import io.jmix.bookstore.employee.territory.screen.TerritoryEdit;
//import io.jmix.bookstore.employee.test_support.Regions;
//import io.jmix.bookstore.employee.test_support.Territories;
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
//class TerritoryBrowseTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//    @Autowired
//    private Territories territories;
//    @Autowired
//    private Regions regions;
//
//    private Territory territory;
//    private ScreenInteractions screenInteractions;
//    private DataGridInteractions<Territory> territoriesDataGrid;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//        databaseCleanup.removeAllEntities(Territory.class);
//
//        Region region = regions.saveDefault();
//        territory = territories.save(
//                territories.defaultData()
//                        .region(region)
//                        .build()
//        );
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        TerritoryBrowse territoryBrowse = screenInteractions.open(TerritoryBrowse.class);
//        territoriesDataGrid = territoryDataGrid(territoryBrowse);
//    }
//
//    @Test
//    void given_oneTerritoryExists_when_openTerritoryBrowse_then_dataGridContainsTheTerritory() {
//
//        // expect:
//        assertThat(territoriesDataGrid.firstItem())
//                .isEqualTo(territory);
//    }
//
//
//    @Test
//    void given_oneTerritoryExists_when_editTerritory_then_editTerritoryEditorIsShown() {
//
//        // given:
//        Territory firstTerritory = territoriesDataGrid.firstItem();
//
//        // and:
//        territoriesDataGrid.edit(firstTerritory);
//
//        // when:
//        var territoryEdit = screenInteractions.findOpenScreen(TerritoryEdit.class);
//
//        // then:
//        assertThat(territoryEdit.getEditedEntity())
//                .isEqualTo(firstTerritory);
//    }
//
//
//    @NotNull
//    private DataGridInteractions<Territory> territoryDataGrid(TerritoryBrowse territoryBrowse) {
//        return DataGridInteractions.of(territoryBrowse, Territory.class, "territoriesTable");
//    }
//}
