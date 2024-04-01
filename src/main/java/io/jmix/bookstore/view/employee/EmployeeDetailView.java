package io.jmix.bookstore.view.employee;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bookstore.employee.Employee;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.action.entitypicker.EntityLookupAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "employees/:id", layout = MainView.class)
@ViewController("bookstore_Employee.detail")
@ViewDescriptor("employee-detail-view.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeDetailView extends StandardDetailView<Employee> {

    @ViewComponent("userField.entityLookup")
    private EntityLookupAction<User> userFieldEntityLookup;

    @ViewComponent("reportsToField.entityLookup")
    private EntityLookupAction<Employee> reportsToFieldEntityLookup;

    @Subscribe(id = "showOnMapBtn", subject = "clickListener")
    public void onShowOnMapBtnClick(final ClickEvent<JmixButton> event) {
        // TODO
    }
}