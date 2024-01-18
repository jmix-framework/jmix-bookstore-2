package io.jmix.bookstore.employee;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum PositionColor implements EnumClass<String> {

    GREEN("GREEN", "position-green"),
    RED("RED", "position-red"),
    DARK_RED("DARK_RED", "position-dark-red" ),
    YELLOW("YELLOW", "position-yellow"),
    DARK_PURPLE("DARK_PURPLE", "position-dark-purple"),
    PURPLE("PURPLE", "position-purple"),
    BLUE("BLUE", "position-blue");

    private String id;
    private String styleName;

    PositionColor(String id, String styleName) {
        this.id = id;
        this.styleName = styleName;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PositionColor fromId(String id) {
        for (PositionColor at : PositionColor.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public String getStyleName() {
        return styleName;
    }
}
