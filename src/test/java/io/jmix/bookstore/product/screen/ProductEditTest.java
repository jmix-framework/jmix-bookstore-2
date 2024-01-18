//package io.jmix.bookstore.product.screen;
//
//import io.jmix.bookstore.product.*;
//import io.jmix.bookstore.product.test_support.ProductData;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.product.test_support.ProductCategories;
//import io.jmix.bookstore.product.test_support.Products;
//import io.jmix.bookstore.test_support.ui.FormInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.core.querycondition.PropertyCondition;
//import io.jmix.ui.Screens;
//import io.jmix.ui.util.OperationResult;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//import static io.jmix.bookstore.entity.Assertions.assertThat;
//
//
//class ProductEditTest extends WebIntegrationTest {
//
//    @Autowired
//    private DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//    @Autowired
//    private Products products;
//    @Autowired
//    private ProductCategories productCategories;
//
//    FormInteractions formInteractions;
//    ScreenInteractions screenInteractions;
//    ProductEdit productEdit;
//
//    @BeforeEach
//    void setUp() {
//        databaseCleanup.removeAllEntities();
//    }
//
//    @Nested
//    class WithOpenedProductEditForm {
//
//        @BeforeEach
//        void setUp(Screens screens) {
//            initProductEditForm(screens);
//        }
//
//        @Test
//        void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved() {
//
//            // given:
//            ProductData productData = products.defaultData().build();
//            formInteractions.setTextFieldValue("nameField", productData.getName());
//            formInteractions.setNumberFieldValue("unitPriceAmountField", productData.getUnitPrice().getAmount());
//            formInteractions.setEnumFieldValue("unitPriceCurrencyField", productData.getUnitPrice().getCurrency());
//
//            // when:
//            OperationResult operationResult = formInteractions.saveForm();
//
//            assertThat(operationResult)
//                    .isEqualTo(OperationResult.success());
//
//            // then:
//            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());
//
//            assertThat(savedProduct)
//                    .isPresent();
//        }
//
//
//        @Test
//        void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved(Screens screens) {
//
//            // given:
//            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//            ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
//            formInteractions = FormInteractions.of(productEdit);
//
//            // and:
//            formInteractions.setTextFieldValue("nameField", null);
//
//            // when:
//            OperationResult operationResult = formInteractions.saveForm();
//
//
//            // then:
//            assertThat(operationResult)
//                    .isEqualTo(OperationResult.fail());
//
//            // and:
//            assertThat(dataManager.load(Product.class).all().list())
//                    .isEmpty();
//
//        }
//
//    }
//
//
//    @Nested
//    class ProductCategoryTests {
//
//        private ProductCategory productCategory1;
//        private ProductCategory productCategory2;
//
//        @BeforeEach
//        void setUp(Screens screens) {
//            productCategory1 = productCategories.saveDefault();
//            productCategory2 = productCategories.saveDefault();
//
//            initProductEditForm(screens);
//        }
//
//        @Test
//        void given_twoProductCategoriesAreInDb_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox() {
//
//            // expect:
//            List<ProductCategory> availableProductCategories = formInteractions.getEntityComboBoxValues("categoryField", ProductCategory.class);
//
//            assertThat(availableProductCategories)
//                    .contains(productCategory1, productCategory2);
//        }
//
//        @Test
//        void given_validProductWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved() {
//
//            // given:
//            ProductData productData = products.defaultData().build();
//            formInteractions.setTextFieldValue("nameField", productData.getName());
//            formInteractions.setNumberFieldValue("unitPriceAmountField", productData.getUnitPrice().getAmount());
//            formInteractions.setEnumFieldValue("unitPriceCurrencyField", productData.getUnitPrice().getCurrency());
//
//            // and:
//            formInteractions.setEntityComboBoxFieldValue("categoryField", productCategory1, ProductCategory.class);
//
//            // when:
//            OperationResult operationResult = formInteractions.saveForm();
//
//            assertThat(operationResult)
//                    .isEqualTo(OperationResult.success());
//
//            // then:
//            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());
//
//            assertThat(savedProduct)
//                    .isPresent()
//                    .get()
//                    .extracting("category")
//                    .isEqualTo(productCategory1);
//        }
//
//    }
//
//    private void initProductEditForm(Screens screens) {
//        screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//        productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
//        formInteractions = FormInteractions.of(productEdit);
//    }
//
//    @NotNull
//    private Optional<Product> findProductByAttribute(String attribute, String value) {
//        return dataManager.load(Product.class)
//                .condition(PropertyCondition.equal(attribute, value))
//                .optional();
//    }
//}
