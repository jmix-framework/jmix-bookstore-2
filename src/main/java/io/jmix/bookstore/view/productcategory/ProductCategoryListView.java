package io.jmix.bookstore.view.productcategory;

import io.jmix.bookstore.product.ProductCategory;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "productCategories", layout = MainView.class)
@ViewController("bookstore_ProductCategory.list")
@ViewDescriptor("product-category-list-view.xml")
@LookupComponent("productCategoriesDataGrid")
@DialogMode(width = "64em")
public class ProductCategoryListView extends StandardListView<ProductCategory> {
}