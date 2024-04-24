package io.jmix.bookstore.product.supplier.creation;

import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.test_environment.TenantCreation;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.ProductCategory;
import io.jmix.bookstore.product.supplier.Supplier;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderRequestStatus;
import io.jmix.bookstore.product.test_support.ProductCategories;
import io.jmix.bookstore.product.test_support.Products;
import io.jmix.bookstore.product.test_support.SupplierOrderRequests;
import io.jmix.bookstore.product.test_support.Suppliers;
import io.jmix.bookstore.test_data.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.multitenancy.entity.Tenant;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static io.jmix.bookstore.entity.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("integration-test")
class SupplierOrderDraftCreationTenantTest {

    private final String TENANT_ID = "tenant-1";
    @Autowired
    SupplierOrderDraftCreation supplierOrderDraftCreation;
    @Autowired
    DataManager dataManager;
    @Autowired
    SupplierOrderRequests supplierOrderRequests;
    @Autowired
    Suppliers suppliers;
    @Autowired
    Products products;
    @Autowired
    ProductCategories productCategories;
    @Autowired
    TenantCreation tenantCreation;
    @Autowired
    SystemAuthenticator systemAuthenticator;
    @Autowired
    DatabaseCleanup databaseCleanup;

    private User tenantAdmin;
    private Product product;

    @BeforeEach
    void setUp() {

        asSystem(() -> {
            databaseCleanup.removeAllEntities();
            databaseCleanup.removeNonAdminUsers();
            databaseCleanup.removeAllEntitiesForTenant(TENANT_ID);
            databaseCleanup.removeAllEntities(Tenant.class);
        });


        tenantAdmin = asSystem(() -> tenantCreation.initialiseTenant(TENANT_ID, TENANT_ID + "|admin"));

        product = asTenantAdmin(this::createProduct);
    }

    private Product createProduct() {

        ProductCategory productCategory = productCategories.saveDefault();

        Supplier supplier = suppliers.saveDefault();

        return products.save(
                products.defaultData()
                        .name("Harry Potter and the Goblet of Fire")
                        .category(productCategory)
                        .supplier(supplier)
                        .build()
        );
    }

    @Test
    void given_supplierOrderRequestHasTenant1_expect_supplierOrderAlsoHasTenant1() {

        asTenantAdmin(() ->
                supplierOrderRequests.save(
                        supplierOrderRequests.defaultData()
                                .requestedBy(tenantAdmin)
                                .product(product)
                                .requestedAmount(200)
                                .comment("Please order new books, they still sell like crazy...")
                                .status(SupplierOrderRequestStatus.NEW)
                                .build()
                )
        );

        // when:
        asSystem(() ->
                supplierOrderDraftCreation.createDraftSupplierOrders()
        );

        // then:
        Optional<SupplierOrder> potentialSupplierOrder = asSystem(this::supplierOrderFromDb);

        assertThat(potentialSupplierOrder)
                .isPresent();

        // and:
        SupplierOrder createdSupplierOrder = potentialSupplierOrder.get();

        assertThat(createdSupplierOrder)
                .hasTenant(TENANT_ID);
    }

    @NotNull
    private Optional<SupplierOrder> supplierOrderFromDb() {
        return dataManager.load(SupplierOrder.class)
                .all()
                .optional();
    }

    private <T> T asTenantAdmin(SystemAuthenticator.AuthenticatedOperation<T> operation) {
        return systemAuthenticator.withUser(TENANT_ID + "|admin", operation);
    }

    private <T> T asSystem(SystemAuthenticator.AuthenticatedOperation<T> operation) {
        return systemAuthenticator.withSystem(operation);
    }

    private void asSystem(Runnable operation) {
        systemAuthenticator.runWithSystem(operation);
    }

}
