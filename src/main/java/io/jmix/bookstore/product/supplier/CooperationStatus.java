package io.jmix.bookstore.product.supplier;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CooperationStatus implements EnumClass<String> {

    CANDIDATE("CANDIDATE"),
    REGULAR("REGULAR"),
    IMPORTANT("IMPORTANT"),
    ON_HOLD("ON_HOLD");

    private String id;

    CooperationStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CooperationStatus fromId(String id) {
        for (CooperationStatus at : CooperationStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
