package io.jmix.bookstore.product.supplier.event;

import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("bookstore_SupplierOrderDraftEventListener")
public class SupplierOrderDraftEventListener {

    protected final ApplicationEventPublisher applicationEventPublisher;
    private final DataManager dataManager;

    public SupplierOrderDraftEventListener(ApplicationEventPublisher applicationEventPublisher, DataManager dataManager) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.dataManager = dataManager;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onSupplierOrderChangedAfterCommit(EntityChangedEvent<SupplierOrder> event) {

        Id<SupplierOrder> supplierOrderRequestId = event.getEntityId();

        SupplierOrder supplierOrder = dataManager.load(supplierOrderRequestId).one();

        if (supplierOrder.getStatus().equals(SupplierOrderStatus.DRAFT)) {
            applicationEventPublisher.publishEvent(new SupplierOrderDraftCreatedEvent(supplierOrder));
        }
    }
}
