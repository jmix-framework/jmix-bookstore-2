package io.jmix.bookstore.view.mytasklistview;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum MyTaskAssignmentType implements EnumClass<String> {

    ALL("All"),
    ASSIGNED_TO_ME("Assigned to me"),
    GROUP("Group");

    private final String id;

    MyTaskAssignmentType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MyTaskAssignmentType fromId(String id) {
        for (MyTaskAssignmentType at : MyTaskAssignmentType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}