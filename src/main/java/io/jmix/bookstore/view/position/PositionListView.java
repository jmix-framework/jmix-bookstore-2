package io.jmix.bookstore.view.position;

import io.jmix.bookstore.employee.Position;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "positions", layout = MainView.class)
@ViewController("bookstore_Position.list")
@ViewDescriptor("position-list-view.xml")
@LookupComponent("positionsDataGrid")
@DialogMode(width = "64em")
public class PositionListView extends StandardListView<Position> {
}