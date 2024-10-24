package io.jmix.bookstore.security;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenant;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.view.employee.EmployeeListView;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;
import io.jmix.uiconstraints.annotation.UiComponentPolicy;
import io.jmix.uiconstraints.annotation.UiComponentPolicyAction;
import io.jmix.uiconstraints.annotation.UiComponentPolicyEffect;

@ResourceRole(name = "Hr", code = Hr.CODE)
public interface Hr {

    String CODE = "hr";

    @UiComponentPolicy(
            viewClass = EmployeeListView.class,
            componentIds = {"employeesDataGrid.create"},
            action = UiComponentPolicyAction.ENABLED,
            effect = UiComponentPolicyEffect.DENY
    )
    @UiComponentPolicy(
            viewClass = EmployeeListView.class,
            componentIds = {"employeesDataGrid.remove"},
            action = UiComponentPolicyAction.VISIBLE,
            effect = UiComponentPolicyEffect.DENY
    )
    @EntityAttributePolicy(entityClass = Employee.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Employee.class, actions = EntityPolicyAction.ALL)
    void employee();

    @EntityAttributePolicy(entityClass = Position.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Position.class, actions = EntityPolicyAction.READ)
    void position();

    @MenuPolicy(menuIds = {"bookstore_FulfillmentCenter.list", "bookstore_ProductCategory.list", "bookstore_Territory.list", "bookstore_Region.list", "bookstore_Position.list", "bookstore_Employee.list"})
    @ViewPolicy(viewIds = {"bookstore_AddressMapView", "bookstore_FulfillmentCenter.list", "bookstore_ProductCategory.list", "bookstore_Territory.list", "bookstore_Region.list", "bookstore_Position.list", "bookstore_Employee.list"})
    void screens();

    @EntityAttributePolicy(entityClass = FulfillmentCenter.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FulfillmentCenter.class, actions = EntityPolicyAction.READ)
    void fulfillmentCenter();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.READ)
    void address();

    @EntityAttributePolicy(entityClass = TestEnvironmentTenant.class, attributes = {"lastLogin", "testdataInitialised"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = TestEnvironmentTenant.class, attributes = {"id", "version", "createTs", "createdBy", "updateTs", "updatedBy", "deleteTs", "deletedBy", "tenantId", "name"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = TestEnvironmentTenant.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void testEnvironmentTenant();

    @SpecificPolicy(resources = {"ui.bulkEdit.enabled", "ui.filter.modifyConfiguration", "ui.filter.modifyJpqlCondition", "ui.filter.modifyGlobalConfiguration"})
    void specific();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.READ)
    void productCategory();

    @EntityAttributePolicy(entityClass = Territory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Territory.class, actions = EntityPolicyAction.READ)
    void territory();
}
