package io.jmix.bookstore.view.territory;

import com.vaadin.flow.router.Route;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.feature.Feature;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.PolygonStyle;
import org.locationtech.jts.geom.Coordinate;

@Route(value = "territories", layout = MainView.class)
@ViewController("bookstore_Territory.list")
@ViewDescriptor("territory-list-view.xml")
@LookupComponent("territoriesDataGrid")
@DialogMode(width = "64em")
public class TerritoryListView extends StandardListView<Territory> {

    @ViewComponent
    private GeoMap map;

    @ViewComponent("map.selectedTerritoryLayer.selectedTerritorySource")
    private VectorSource selectedTerritorySource;

    @ViewComponent("map.territoriesLayer.territoriesSource")
    private DataVectorSource<Territory> territoriesSource;

    @ViewComponent
    private JmixCheckbox zoomCheckbox;
    @ViewComponent
    private DataGrid<Territory> territoriesDataGrid;

    @Subscribe
    public void onInit(final InitEvent event) {
        territoriesSource.addGeoObjectClickListener(clickEvent -> {
            territoriesDataGrid.getSelectionModel().select(clickEvent.getItem());
        });
        selectedTerritorySource.addSourceFeatureClickListener(clickEvent -> {
            territoriesDataGrid.getSelectionModel().deselectAll();
        });
    }

    @Subscribe(id = "territoriesDc", target = Target.DATA_CONTAINER)
    public void onTerritoriesDcItemChange(final InstanceContainer.ItemChangeEvent<Territory> event) {
        selectedTerritorySource.removeAllFeatures();
        Territory territory = event.getItem();
        if (territory != null) {
            PolygonFeature feature = new PolygonFeature(territory.getGeographicalArea())
                    .withStyles(new PolygonStyle().withFill(new Fill("#FFC8D0")).build());
            selectedTerritorySource.addFeature(feature);

            if (Boolean.TRUE.equals(zoomCheckbox.getValue())) {
                map.zoomToFeature(feature);
            }
        } else {
            map.setCenter(new Coordinate(-99.755859, 39.164141));
            map.setZoom(4.0);
        }
    }
}