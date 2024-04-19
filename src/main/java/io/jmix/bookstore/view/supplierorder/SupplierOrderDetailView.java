package io.jmix.bookstore.view.supplierorder;

import io.jmix.bookstore.product.supplier.SupplierOrder;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "supplierOrders/:id", layout = MainView.class)
@ViewController("bookstore_SupplierOrder.detail")
@ViewDescriptor("supplier-order-detail-view.xml")
@EditedEntityContainer("supplierOrderDc")
public class SupplierOrderDetailView extends StandardDetailView<SupplierOrder> {
}