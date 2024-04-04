package io.jmix.bookstore.view.productcategory;

import io.jmix.bookstore.product.ProductCategory;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "productCategories/:id", layout = MainView.class)
@ViewController("bookstore_ProductCategory.detail")
@ViewDescriptor("product-category-detail-view.xml")
@EditedEntityContainer("productCategoryDc")
public class ProductCategoryDetailView extends StandardDetailView<ProductCategory> {
}