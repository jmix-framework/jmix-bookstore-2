package io.jmix.bookstore.security;

import io.jmix.bookstore.customer.Customer;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Money;
import io.jmix.bookstore.fulfillment.FulfillmentCenter;
import io.jmix.bookstore.order.entity.Order;
import io.jmix.bookstore.order.entity.OrderLine;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

/**
 * functional role for 'Order Fulfillment' position
 * <ul>
 *   <li>read & write access to Customer & Order data; supplier order requests</li>
 *   <li>read access to Product and supplier order data</li>
 *   <li>access to corresponding UI screens</li>
 * </ul>
 */
@ResourceRole(name = "Order Fulfillment", code = OrderFulfillmentRole.CODE)
public interface OrderFulfillmentRole {
    String CODE = "order-fulfillment";

    @EntityAttributePolicy(entityClass = SupplierOrderRequest.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrderRequest.class, actions = EntityPolicyAction.ALL)
    void supplierOrderRequest();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.READ)
    void product();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.READ)
    void productCategory();

    @MenuPolicy(menuIds = {"bookstore_Supplier.list", "bookstore_SupplierOrder.list", "bookstore_Order.list", "bookstore_Customer.list", "bookstore_Product.list"})
    @ViewPolicy(viewIds = {"bookstore_Supplier.detail", "bookstore_Supplier.list", "bookstore_SupplierOrderRequest.detail", "bookstore_SupplierOrder.list", "bookstore_SupplierOrder.detail", "bookstore_SupplierOrderReviewForm", "bookstore_Order.list", "bookstore_Customer.list", "bookstore_Product.list", "bookstore_Order.detail", "bookstore_OrderLine.detail", "bookstore_Customer.detail", "bookstore_Customer.lookup", "bookstore_Order.confirm", "bookstore_TrackDeliveryMapView", "bookstore_Product.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Supplier.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Supplier.class, actions = EntityPolicyAction.READ)
    void supplier();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();

    @EntityAttributePolicy(entityClass = OrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = OrderLine.class, actions = EntityPolicyAction.ALL)
    void orderLine();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = Address.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Address.class, actions = EntityPolicyAction.ALL)
    void address();

    @EntityAttributePolicy(entityClass = SupplierOrder.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrder.class, actions = EntityPolicyAction.READ)
    void supplierOrder();

    @EntityAttributePolicy(entityClass = SupplierOrderLine.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrderLine.class, actions = EntityPolicyAction.READ)
    void supplierOrderLine();

    @EntityAttributePolicy(entityClass = FulfillmentCenter.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FulfillmentCenter.class, actions = EntityPolicyAction.READ)
    void fulfillmentCenter();

    @EntityAttributePolicy(entityClass = Money.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Money.class, actions = EntityPolicyAction.ALL)
    void money();
}
