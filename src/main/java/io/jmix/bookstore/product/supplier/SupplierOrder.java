package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER_ORDER", indexes = {
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_ORDER_SUPPLIER", columnList = "SUPPLIER_ID")
})
@Entity(name = "bookstore_SupplierOrder")
public class SupplierOrder extends StandardTenantEntity {

    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Supplier supplier;

    @Column(name = "STATUS", nullable = false)
    @NotNull
    private String status;

    @Column(name = "ORDER_DATE", nullable = false)
    @NotNull
    private LocalDate orderDate;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "supplierOrder", cascade = CascadeType.ALL)
    private List<SupplierOrderLine> orderLines;


    @Column(name = "ORDER_FORM")
    @Lob
    private FileRef orderForm;

    public FileRef getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(FileRef orderForm) {
        this.orderForm = orderForm;
    }

    public SupplierOrderStatus getStatus() {
        return status == null ? null : SupplierOrderStatus.fromId(status);
    }

    public void setStatus(SupplierOrderStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public List<SupplierOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<SupplierOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @InstanceName
    @DependsOnProperties({"supplier", "orderDate"})
    public String getInstanceName(DatatypeFormatter datatypeFormatter, MetadataTools metadataTools) {
        return String.format("%s - %s", metadataTools.getInstanceName(supplier), datatypeFormatter.formatLocalDate(orderDate));
    }
}
