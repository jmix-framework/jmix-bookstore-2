package io.jmix.bookstore.security;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

/**
 * functional role for 'Sales Representative' position
 * <ul>
 *   <li>read & write access to Customer & Order data; supplier order requests</li>
 *   <li>read access to product catalog data</li>
 *   <li>access to corresponding UI screens</li>
 *   <li>access to corresponding UI screens</li>
 * </ul>
 */
@ResourceRole(name = "Sales Representative", code = SalesRepresentativeRole.CODE)
public interface SalesRepresentativeRole {
    String CODE = "sales-representative";

    @MenuPolicy(menuIds = {"bookstore_Order.list", "bookstore_Customer.list", "bookstore_Product.list", "bookstore_Territory.list", "bookstore_Region.list", "bookstore_Employee.list", "bookstore_ProductCategory.list"})
    @ViewPolicy(viewIds = {"bookstore_Order.list", "bookstore_Customer.list", "bookstore_Product.list", "bookstore_Territory.list", "bookstore_Region.list", "bookstore_Order.detail", "bookstore_OrderLine.detail", "bookstore_Customer.detail", "bookstore_Employee.list", "bookstore_Employee.detail", "bookstore_ProductCategory.list", "bookstore_Product.detail", "bookstore_ProductCategory.detail", "bookstore_TrackDeliveryMap", "bookstore_Customer.lookup"})
    void screens();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = OrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = OrderLine.class, actions = EntityPolicyAction.ALL)
    void orderLine();

    @EntityAttributePolicy(entityClass = Position.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Position.class, actions = EntityPolicyAction.READ)
    void position();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.READ)
    void product();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.READ)
    void productCategory();

    @EntityAttributePolicy(entityClass = Region.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Region.class, actions = EntityPolicyAction.READ)
    void region();

    @EntityAttributePolicy(entityClass = Territory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Territory.class, actions = EntityPolicyAction.READ)
    void territory();

    @EntityAttributePolicy(entityClass = Money.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Money.class, actions = EntityPolicyAction.ALL)
    void money();
}
