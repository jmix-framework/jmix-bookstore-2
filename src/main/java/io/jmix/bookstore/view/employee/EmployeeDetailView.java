package io.jmix.bookstore.view.employee;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bookstore.employee.Employee;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.view.addressmap.AddressMapView;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.action.entitypicker.EntityLookupAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "employees/:id", layout = MainView.class)
@ViewController("bookstore_Employee.detail")
@ViewDescriptor("employee-detail-view.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeDetailView extends StandardDetailView<Employee> {

    @ViewComponent("userField.entityLookup")
    private EntityLookupAction<User> userFieldEntityLookup;

    @ViewComponent("reportsToField.entityLookup")
    private EntityLookupAction<Employee> reportsToFieldEntityLookup;

    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe(id = "showOnMapBtn", subject = "clickListener")
    public void onShowOnMapBtnClick(final ClickEvent<JmixButton> event) {
        DialogWindow<AddressMapView> window = dialogWindows.view(this, AddressMapView.class).build();
        window.getView().setAddress(getEditedEntity().getAddress());
        window.open();
    }
}