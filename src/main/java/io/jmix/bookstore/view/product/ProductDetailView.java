package io.jmix.bookstore.view.product;

import io.jmix.bookstore.product.Product;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "products/:id", layout = MainView.class)
@ViewController("bookstore_Product.detail")
@ViewDescriptor("product-detail-view.xml")
@EditedEntityContainer("productDc")
public class ProductDetailView extends StandardDetailView<Product> {
}