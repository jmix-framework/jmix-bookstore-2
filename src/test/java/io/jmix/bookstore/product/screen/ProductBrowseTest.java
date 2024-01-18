//package io.jmix.bookstore.product.screen;
//
//import io.jmix.bookstore.product.Product;
//import io.jmix.bookstore.product.test_support.Products;
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
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ProductBrowseTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//
//    @Autowired
//    Products products;
//
//    private Product product;
//    private DataGridInteractions<Product> productDataGrid;
//    private ScreenInteractions screenInteractions;
//
//
//    @BeforeEach
//    void setUp(Screens screens) {
//
//        databaseCleanup.removeAllEntities();
//
//        product = products.saveDefault();
//
//        screenInteractions = ScreenInteractions.forBrowse(screens);
//        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);
//        productDataGrid = productDataGrid(productBrowse);
//    }
//
//    @Test
//    void given_oneProductExists_when_openProductBrowse_then_tableContainsTheProduct() {
//
//        // expect:
//        assertThat(productDataGrid.firstItem())
//                .isEqualTo(product);
//    }
//
//
//    @Test
//    void given_oneProductExists_when_editProduct_then_editProductEditorIsShown() {
//
//        // and:
//        Product firstProduct = productDataGrid.firstItem();
//
//        // and:
//        productDataGrid.edit(firstProduct);
//
//        // then:
//        ProductEdit productEdit = screenInteractions.findOpenScreen(ProductEdit.class);
//
//        assertThat(productEdit.getEditedEntity())
//                .isEqualTo(firstProduct);
//    }
//
//    @NotNull
//    private DataGridInteractions<Product> productDataGrid(ProductBrowse productBrowse) {
//        return DataGridInteractions.of(productBrowse, Product.class, "productsTable");
//    }
//}
