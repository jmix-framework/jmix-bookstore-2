package io.jmix.bookstore.view.product;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;

import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestStatus;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;

@Route(value = "products", layout = MainView.class)
@ViewController("bookstore_Product.list")
@ViewDescriptor("product-list-view.xml")
@LookupComponent("productsDataGrid")
@DialogMode(width = "64em")
public class ProductListView extends StandardListView<Product> {

    @ViewComponent
    private DataGrid<Product> productsDataGrid;

    @Subscribe("productsDataGrid.fillUpInventory")
    public void onProductsDataGridFillUpInventory(final ActionPerformedEvent event) {
        // TODO fill up inventory
//        SupplierOrderRequestEdit supplierOrderRequestEdit = screenBuilders.editor(SupplierOrderRequest.class, this)
//                .withScreenClass(SupplierOrderRequestEdit.class)
//                .withInitializer(supplierOrderRequest -> {
//                    supplierOrderRequest.setRequestedAmount(100);
//                    supplierOrderRequest.setStatus(SupplierOrderRequestStatus.NEW);
//                    supplierOrderRequest.setProduct(productsTable.getSingleSelected());
//
//                    User user = (User) currentAuthentication.getUser();
//                    supplierOrderRequest.setRequestedBy(user);
//                })
//                .build();
//
//        supplierOrderRequestEdit.addAfterCloseListener(afterCloseEvent -> {
//            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
//                supplierOrderRequestCreatedConfirmation.show();
//            }
//        });
//
//        supplierOrderRequestEdit
//                .show();
    }

    @Install(to = "productsDataGrid.fillUpInventory", subject = "enabledRule")
    private boolean productsDataGridFillUpInventoryEnabledRule() {
        return productsDataGrid.getSingleSelectedItem() != null
                && Boolean.TRUE.equals(productsDataGrid.getSingleSelectedItem().getActive());
    }
}