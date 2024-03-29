package io.jmix.bookstore.view.territory;

import io.jmix.bookstore.employee.Territory;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "territories/:id", layout = MainView.class)
@ViewController("bookstore_Territory.detail")
@ViewDescriptor("territory-detail-view.xml")
@EditedEntityContainer("territoryDc")
public class TerritoryDetailView extends StandardDetailView<Territory> {
}