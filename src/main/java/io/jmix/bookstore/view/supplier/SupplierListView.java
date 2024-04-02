package io.jmix.bookstore.view.supplier;

import io.jmix.bookstore.product.supplier.Supplier;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "suppliers", layout = MainView.class)
@ViewController("bookstore_Supplier.list")
@ViewDescriptor("supplier-list-view.xml")
@LookupComponent("suppliersDataGrid")
@DialogMode(width = "64em")
public class SupplierListView extends StandardListView<Supplier> {
}