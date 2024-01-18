package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum SupplierOrderStatus implements EnumClass<String> {

    DRAFT("DRAFT"),
    VALID("VALID"),
    INVALID("INVALID"),
    APPROVED("APPROVED"),
    CHANGES_REQUIRED("CHANGES_REQUIRED"),
    ORDERED("ORDERED"),
    DELIVERED("DELIVERED");

    private String id;

    SupplierOrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SupplierOrderStatus fromId(String id) {
        for (SupplierOrderStatus at : SupplierOrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
