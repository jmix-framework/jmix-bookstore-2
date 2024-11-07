package io.jmix.bookstore.view.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.facet.UrlQueryParametersFacet;
import io.jmix.flowui.facet.urlqueryparameters.AbstractUrlQueryParametersBinder;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainView.class)
@ViewController("bookstore_Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {

    @ViewComponent
    private JmixTabSheet tabSheet;
    @ViewComponent
    private UrlQueryParametersFacet urlQueryParameters;
    @ViewComponent("map.vectorLayer.vectorSource")
    private DataVectorSource<Customer> vectorSource;
    @Autowired
    private ViewNavigators viewNavigators;

    @Subscribe
    public void onInit(final InitEvent event) {
        urlQueryParameters.registerBinder(new TabFocusBinder());

        vectorSource.addGeoObjectClickListener(clickEvent -> {
            Customer customer = clickEvent.getItem();
            viewNavigators.detailView(this, Customer.class)
                    .editEntity(customer)
                    .navigate();
        });
    }

    private class TabFocusBinder extends AbstractUrlQueryParametersBinder {

        public TabFocusBinder() {
            tabSheet.addSelectedChangeListener(event -> {
                QueryParameters queryParameters = QueryParameters.of("focusTab", event.getSelectedTab().getId().orElse(null));
                fireQueryParametersChanged(new UrlQueryParametersFacet.UrlQueryParametersChangeEvent(this, queryParameters));
            });
        }

        @Override
        public Component getComponent() {
            return null;
        }

        @Override
        public void updateState(QueryParameters queryParameters) {
            queryParameters.getSingleParameter("focusTab").ifPresent(value -> {
                if ("mapTab".equals(value)) {
                    tabSheet.setSelectedIndex(1);
                }
            });
        }
    }
}