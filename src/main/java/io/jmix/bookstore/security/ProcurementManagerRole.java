package io.jmix.bookstore.security;

import io.jmix.bookstore.fulfillment.FulfillmentCenter;
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

/**
 * functional role for 'Procurement Manager' position
 * <ul>
 *   <li>read & write access to product catalog data</li>
 *   <li>read & write access to supplier order data</li>
 *   <li>read access to supplier order request data</li>
 *   <li>access to corresponding UI screens</li>
 * </ul>
 */
@ResourceRole(name = "Procurement Manager", code = ProcurementManagerRole.CODE)
public interface ProcurementManagerRole {
    String CODE = "procurement-manager";

    @EntityAttributePolicy(entityClass = Supplier.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Supplier.class, actions = EntityPolicyAction.ALL)
    void supplier();

    @EntityAttributePolicy(entityClass = SupplierOrder.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrder.class, actions = EntityPolicyAction.ALL)
    void supplierOrder();

    @EntityAttributePolicy(entityClass = SupplierOrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = SupplierOrderLine.class, actions = EntityPolicyAction.ALL)
    void supplierOrderLine();

    @EntityAttributePolicy(entityClass = SupplierOrderRequest.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = SupplierOrderRequest.class, actions = EntityPolicyAction.READ)
    void supplierOrderRequest();

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.ALL)
    void product();

    @EntityAttributePolicy(entityClass = ProductCategory.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ProductCategory.class, actions = EntityPolicyAction.ALL)
    void productCategory();

//    @MenuPolicy(menuIds = {"bookstore_Supplier.browse", "bookstore_SupplierOrder.browse", "bookstore_Product.browse", "bookstore_ProductCategory.browse"})
//    @ScreenPolicy(screenIds = {"bookstore_Supplier.edit", "bookstore_Supplier.browse", "bookstore_SupplierOrderRequest.edit", "bookstore_SupplierOrder.browse", "bookstore_SupplierOrder.edit", "bookstore_MainScreen", "bookstore_LoginScreen", "bookstore_SupplierOrder.approval", "bookstore_Product.browse", "bookstore_ProductCategory.browse", "bookstore_ProductCategory.edit", "bookstore_Product.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = FulfillmentCenter.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = FulfillmentCenter.class, actions = EntityPolicyAction.READ)
    void fulfillmentCenter();
}
