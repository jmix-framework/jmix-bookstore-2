//package io.jmix.bookstore.product.screen;
//
//import io.jmix.bookstore.product.ProductCategory;
//import io.jmix.bookstore.product.test_support.ProductCategoryData;
//import io.jmix.bookstore.product.productcategory.screen.ProductCategoryEdit;
//import io.jmix.bookstore.test_data.DatabaseCleanup;
//import io.jmix.bookstore.product.test_support.ProductCategories;
//import io.jmix.bookstore.test_support.ui.FormInteractions;
//import io.jmix.bookstore.test_support.ui.ScreenInteractions;
//import io.jmix.bookstore.test_support.ui.WebIntegrationTest;
//import io.jmix.core.DataManager;
//import io.jmix.core.querycondition.PropertyCondition;
//import io.jmix.ui.Screens;
//import io.jmix.ui.util.OperationResult;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ProductCategoryEditTest extends WebIntegrationTest {
//
//    @Autowired
//    DataManager dataManager;
//    @Autowired
//    DatabaseCleanup databaseCleanup;
//    @Autowired
//    private ProductCategories productCategories;
//
//    @BeforeEach
//    void setUp() {
//        databaseCleanup.removeAllEntities(ProductCategory.class);
//    }
//
//    FormInteractions formInteractions;
//
//    @Test
//    void given_validProductCategory_when_saveProductCategoryThroughTheForm_then_productCategoryIsSaved(Screens screens) {
//
//        // given:
//        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//        ProductCategoryEdit productCategoryEdit = screenInteractions.openEditorForCreation(ProductCategoryEdit.class, ProductCategory.class);
//        formInteractions = FormInteractions.of(productCategoryEdit);
//
//        // and:
//        ProductCategoryData categoryData = productCategories.defaultData().build();
//        formInteractions.setTextFieldValue("nameField", categoryData.getName());
//
//        // when:
//        OperationResult operationResult = formInteractions.saveForm();
//
//        assertThat(operationResult)
//                .isEqualTo(OperationResult.success());
//
//        // then:
//        Optional<ProductCategory> savedProductCategory = findProductCategoryByAttribute("name", categoryData.getName());
//
//        assertThat(savedProductCategory)
//                .isPresent();
//    }
//
//    @Test
//    void given_productCategoryWithoutName_when_saveProductCategoryThroughTheForm_then_productCategoryIsNotSaved(Screens screens) {
//
//        // given:
//        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
//        ProductCategoryEdit productCategoryEdit = screenInteractions.openEditorForCreation(ProductCategoryEdit.class, ProductCategory.class);
//        formInteractions = FormInteractions.of(productCategoryEdit);
//
//        // and:
//        formInteractions.setTextFieldValue("nameField", null);
//
//        // when:
//        OperationResult operationResult = formInteractions.saveForm();
//
//
//        // then:
//        assertThat(operationResult)
//                .isEqualTo(OperationResult.fail());
//
//        // and:
//        assertThat(dataManager.load(ProductCategory.class).all().list())
//                .isEmpty();
//
//    }
//
//    @NotNull
//    private Optional<ProductCategory> findProductCategoryByAttribute(String attribute, String value) {
//        return dataManager.load(ProductCategory.class)
//                .condition(PropertyCondition.equal(attribute, value))
//                .optional();
//    }
//}
