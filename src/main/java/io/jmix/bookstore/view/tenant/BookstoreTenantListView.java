package io.jmix.bookstore.view.tenant;

import io.jmix.bookstore.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.multitenancyflowui.view.tenant.TenantListView;

@Route(value = "mten/tenants", layout = MainView.class)
@ViewController("mten_Tenant.list")
@ViewDescriptor("bookstore-tenant-list-view.xml")
public class BookstoreTenantListView extends TenantListView {
}