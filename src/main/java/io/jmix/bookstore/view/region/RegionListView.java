package io.jmix.bookstore.view.region;

import io.jmix.bookstore.employee.Region;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "regions", layout = MainView.class)
@ViewController("bookstore_Region.list")
@ViewDescriptor("region-list-view.xml")
@LookupComponent("regionsDataGrid")
@DialogMode(width = "64em")
public class RegionListView extends StandardListView<Region> {
}