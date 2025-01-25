package io.jmix.bookstore.product;

import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_PRODUCT", indexes = {
        @Index(name = "IDX_BOOKSTORE_PRODUCT_SUPPLIER", columnList = "SUPPLIER_ID"),
        @Index(name = "IDX_BOOKSTORE_PRODUCT_TENANT", columnList = "TENANT")
})
@Entity(name = "bookstore_Product")
public class Product extends StandardTenantEntity {
    @NotNull
    @InstanceName
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory category;


    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "UNIT_PRICE_AMOUNT", nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "UNIT_PRICE_CURRENCY", nullable = false))
    })
    private Money unitPrice;

    @Column(name = "UNITS_IN_STOCK")
    private Integer unitsInStock;

    @Column(name = "UNITS_ON_ORDER")
    private Integer unitsOnOrder;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = false;

    @JoinColumn(name = "SUPPLIER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Integer unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
