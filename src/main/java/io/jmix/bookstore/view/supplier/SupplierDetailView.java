package io.jmix.bookstore.view.supplier;

import io.jmix.bookstore.product.supplier.Supplier;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "suppliers/:id", layout = MainView.class)
@ViewController("bookstore_Supplier.detail")
@ViewDescriptor("supplier-detail-view.xml")
@EditedEntityContainer("supplierDc")
public class SupplierDetailView extends StandardDetailView<Supplier> {
}