//package io.jmix.bookstore.product.screen;
//
//import io.jmix.bookstore.product.ProductCategory;
//import io.jmix.bookstore.product.productcategory.screen.ProductCategoryBrowse;
//import io.jmix.bookstore.product.productcategory.screen.ProductCategoryEdit;
//import io.jmix.bookstore.product.test_support.ProductCategories;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.DataGridInteractions;
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
//class ProductCategoryBrowseTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    private ProductCategory productCategory;
//    @Autowired
//    private ProductCategories productCategories;
//    private ScreenInteractions screenInteractions;
//    private DataGridInteractions<ProductCategory> productCategoryDataGrid;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//        productCategory = productCategories.saveDefault();
//
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        ProductCategoryBrowse productCategoryBrowse = screenInteractions.open(ProductCategoryBrowse.class);
//        productCategoryDataGrid = productCategoryDataGrid(productCategoryBrowse);
//    }
//
//    @Test
//    void given_oneProductCategoryExists_when_openProductCategoryBrowse_then_tableContainsTheProductCategory() {
//
//        // expect:
//        assertThat(productCategoryDataGrid.firstItem())
//                .isEqualTo(productCategory);
//    }
//
//
//    @Test
//    void given_oneProductCategoryExists_when_editProductCategory_then_editProductCategoryEditorIsShown() {
//
//        // given:
//        ProductCategory firstProductCategory = productCategoryDataGrid.firstItem();
//
//        // and:
//        productCategoryDataGrid.edit(firstProductCategory);
//
//        // then:
//        ProductCategoryEdit productCategoryEdit = screenInteractions.findOpenScreen(ProductCategoryEdit.class);
//
//        assertThat(productCategoryEdit.getEditedEntity())
//                .isEqualTo(firstProductCategory);
//    }
//
//    @NotNull
//    private DataGridInteractions<ProductCategory> productCategoryDataGrid(ProductCategoryBrowse productCategoryBrowse) {
//        return DataGridInteractions.of(productCategoryBrowse, ProductCategory.class, "productCategoriesTable");
//    }
//}
