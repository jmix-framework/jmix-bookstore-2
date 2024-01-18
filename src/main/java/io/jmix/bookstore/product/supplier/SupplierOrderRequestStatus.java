package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum SupplierOrderRequestStatus implements EnumClass<String> {

    NEW("NEW"),
    ORDER_LINE_CREATED("ORDER_LINE_CREATED");

    private String id;

    SupplierOrderRequestStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SupplierOrderRequestStatus fromId(String id) {
        for (SupplierOrderRequestStatus at : SupplierOrderRequestStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
