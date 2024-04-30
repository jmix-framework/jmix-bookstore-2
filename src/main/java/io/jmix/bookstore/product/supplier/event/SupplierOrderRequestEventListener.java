package io.jmix.bookstore.product.supplier.event;

import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("bookstore_SupplierOrderRequestEventListener")
public class SupplierOrderRequestEventListener {

    protected final ApplicationEventPublisher applicationEventPublisher;
    private final DataManager dataManager;

    public SupplierOrderRequestEventListener(ApplicationEventPublisher applicationEventPublisher, DataManager dataManager) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.dataManager = dataManager;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onSupplierOrderRequestChangedAfterCommit(EntityChangedEvent<SupplierOrderRequest> event) {

        Id<SupplierOrderRequest> supplierOrderRequestId = event.getEntityId();

        SupplierOrderRequest supplierOrderRequest = dataManager.load(supplierOrderRequestId).one();

        applicationEventPublisher.publishEvent(new SupplierOrderRequestCreatedEvent(supplierOrderRequest));
    }
}
