package io.jmix.bookstore.view.region;

import io.jmix.bookstore.employee.Region;

import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "regions/:id", layout = MainView.class)
@ViewController("bookstore_Region.detail")
@ViewDescriptor("region-detail-view.xml")
@EditedEntityContainer("regionDc")
public class RegionDetailView extends StandardDetailView<Region> {
}