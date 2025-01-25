package io.jmix.bookstore.perftests;

import com.vaadin.flow.component.details.Details;
import io.jmix.flowui.component.main.JmixListMenu;

public class BookstoreListMenu extends JmixListMenu {

    @Override
    protected Details createMenuBarComponent(MenuBarItem menuBarItem) {
        Details details = super.createMenuBarComponent(menuBarItem);
        details.setId(menuBarItem.getId() + "Details");
        return details;
    }
}
