package io.jmix.bookstore.view.orderline;

import com.vaadin.flow.component.AbstractField;
import io.jmix.bookstore.entity.Currency;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.entity.OrderLine;

import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.view.*;

import java.math.BigDecimal;

@Route(value = "orderLines/:id", layout = MainView.class)
@ViewController("bookstore_OrderLine.detail")
@ViewDescriptor("order-line-detail-view.xml")
@EditedEntityContainer("orderLineDc")
@DialogMode(width = "40em")
public class OrderLineDetailView extends StandardDetailView<OrderLine> {

    @Subscribe
    public void onInitEntity(final InitEntityEvent<OrderLine> event) {
        event.getEntity().setQuantity(1);
    }

    @Subscribe("productField")
    public void onProductFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Product>, Product> event) {
        Money orderLineUnitPrice = getEditedEntity().getUnitPrice();
        Money orderLineDiscount = getEditedEntity().getDiscount();

        if (event.getValue() != null) {
            Money productUnitPrice = getEditedEntity().getProduct().getUnitPrice();
            Currency currency = productUnitPrice.getCurrency();

            orderLineUnitPrice.setCurrency(currency);
            orderLineUnitPrice.setAmount(productUnitPrice.getAmount());

            orderLineDiscount.setAmount(BigDecimal.ZERO);
            orderLineDiscount.setCurrency(currency);
        }
        else {
            orderLineUnitPrice.setAmount(null);
            orderLineUnitPrice.setCurrency(null);

            orderLineDiscount.setAmount(null);
            orderLineDiscount.setCurrency(null);
        }
    }
}