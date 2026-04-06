package io.jmix.bookstore.icon;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.jmix.flowui.icon.impl.IconsImpl;
import org.springframework.context.annotation.Primary;

//@Primary
//@org.springframework.stereotype.Component("IconsExt")
public class IconsExt extends IconsImpl {

    @Override
    protected Component createIconByName(String iconName) {
        return switch (iconName) {
            case "EDIT" -> LumoIcon.EDIT.create();
            case "COG" -> LumoIcon.COG.create();
            case "USER" -> LumoIcon.USER.create();
            default -> super.createIconByName(iconName);
        };
    }
}
