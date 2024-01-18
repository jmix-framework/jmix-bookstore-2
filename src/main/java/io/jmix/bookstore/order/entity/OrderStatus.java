package io.jmix.bookstore.order.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum OrderStatus implements EnumClass<String> {

    NEW("NEW"),
    CONFIRMED("CONFIRMED"),
    IN_DELIVERY("IN_DELIVERY"),
    DELIVERED("DELIVERED");

    private String id;

    OrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static OrderStatus fromId(String id) {
        for (OrderStatus at : OrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
