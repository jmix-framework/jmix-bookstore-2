package io.jmix.bookstore.view.supplierorderrequest;

import io.jmix.bookstore.product.supplier.SupplierOrderRequest;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "supplierOrderRequests/:id", layout = MainView.class)
@ViewController("bookstore_SupplierOrderRequest.detail")
@ViewDescriptor("supplier-order-request-detail-view.xml")
@EditedEntityContainer("supplierOrderRequestDc")
public class SupplierOrderRequestDetailView extends StandardDetailView<SupplierOrderRequest> {
}