package io.jmix.bookstore.view.product;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestStatus;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.bookstore.view.supplierorderrequest.SupplierOrderRequestDetailView;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "products", layout = MainView.class)
@ViewController("bookstore_Product.list")
@ViewDescriptor("product-list-view.xml")
@LookupComponent("productsDataGrid")
@DialogMode(width = "64em")
public class ProductListView extends StandardListView<Product> {

    @ViewComponent
    private DataGrid<Product> productsDataGrid;

    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe("productsDataGrid.fillUpInventory")
    public void onProductsDataGridFillUpInventory(final ActionPerformedEvent event) {
        dialogWindows.detail(this, SupplierOrderRequest.class)
                .withViewClass(SupplierOrderRequestDetailView.class)
                .withInitializer(supplierOrderRequest -> {
                    supplierOrderRequest.setRequestedAmount(100);
                    supplierOrderRequest.setStatus(SupplierOrderRequestStatus.NEW);
                    supplierOrderRequest.setProduct(productsDataGrid.getSingleSelectedItem());

                    User user = (User) currentAuthentication.getUser();
                    supplierOrderRequest.setRequestedBy(user);
                })
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        notifications.create(messageBundle.getMessage("supplierOrderRequestCreatedConfirmation"))
                                .withPosition(Notification.Position.BOTTOM_END)
                                .show();
                    }
                })
                .open();
    }

    @Install(to = "productsDataGrid.fillUpInventory", subject = "enabledRule")
    private boolean productsDataGridFillUpInventoryEnabledRule() {
        return productsDataGrid.getSingleSelectedItem() != null
                && Boolean.TRUE.equals(productsDataGrid.getSingleSelectedItem().getActive());
    }
}