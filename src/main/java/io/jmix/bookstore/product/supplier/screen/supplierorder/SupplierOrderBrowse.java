package io.jmix.bookstore.product.supplier.screen.supplierorder;

import io.jmix.bookstore.product.supplier.PerformSupplierOrderService;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("bookstore_SupplierOrder.browse")
@UiDescriptor("supplier-order-browse.xml")
@LookupComponent("supplierOrdersTable")
public class SupplierOrderBrowse extends StandardLookup<SupplierOrder> {
    @Autowired
    private PerformSupplierOrderService performSupplierOrderService;
    @Autowired
    private NotificationFacet supplierOrdersFromRequestsCreatedConfirmation;

    @Subscribe("supplierOrdersTable.createSupplierOrdersFromRequests")
    public void onSupplierOrdersTableCreateSupplierOrdersFromRequests(Action.ActionPerformedEvent event) {
        performSupplierOrderService.createDraftSupplierOrders();

        getScreenData().loadAll();

        supplierOrdersFromRequestsCreatedConfirmation.show();
    }

}