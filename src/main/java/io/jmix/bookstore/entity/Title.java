package io.jmix.bookstore.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Title implements EnumClass<String> {

    MR("MR"),
    MRS("MRS");

    private String id;

    Title(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Title fromId(String id) {
        for (Title at : Title.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
