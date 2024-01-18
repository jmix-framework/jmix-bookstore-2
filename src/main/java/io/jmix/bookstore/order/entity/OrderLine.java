package io.jmix.bookstore.order.entity;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.product.Product;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDERLINE_ORDER_ID", columnList = "ORDER_ID"),
        @Index(name = "IDX_BOOKSTOREORDERLINE_PRODUCT", columnList = "PRODUCT_ID")
})
@Entity(name = "bookstore_OrderLine")
public class OrderLine extends StandardTenantEntity {

    @NotNull
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @InstanceName
    @NotNull
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "UNIT_PRICE_AMOUNT", nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "UNIT_PRICE_CURRENCY", nullable = false))
    })
    private Money unitPrice;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "DISCOUNT_AMOUNT", nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "DISCOUNT_CURRENCY", nullable = false))
    })
    private Money discount;

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
