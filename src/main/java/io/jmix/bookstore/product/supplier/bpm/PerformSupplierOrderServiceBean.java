package io.jmix.bookstore.product.supplier.bpm;

import io.jmix.reports.yarg.reporting.ReportOutputDocument;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.product.Product;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderLine;
import io.jmix.bookstore.product.supplier.SupplierOrderRequest;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Id;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import io.jmix.reports.runner.ReportRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component(value = PerformSupplierOrderService.NAME)
public class PerformSupplierOrderServiceBean implements PerformSupplierOrderService {
    private static final Logger log = LoggerFactory.getLogger(PerformSupplierOrderServiceBean.class);
    protected final NotificationManager notificationManager;
    private final DataManager dataManager;
    private final DatatypeFormatter datatypeFormatter;
    private final ReportRunner reportRunner;
    private final FileStorage fileStorage;

    public PerformSupplierOrderServiceBean(
            DataManager dataManager,
            NotificationManager notificationManager,
            DatatypeFormatter datatypeFormatter,
            ReportRunner reportRunner,
            FileStorage fileStorage
    ) {
        this.dataManager = dataManager;
        this.notificationManager = notificationManager;
        this.datatypeFormatter = datatypeFormatter;
        this.reportRunner = reportRunner;
        this.fileStorage = fileStorage;
    }


    @Override
    public void notifyRequestersAboutInvalidRequest(SupplierOrder supplierOrder) {

        log.info("Notifying requesters about invalid requests for supplier Order: {}", supplierOrder);

        supplierOrder.getOrderLines().stream().map(this::declinedRequestNotification)
                .forEach(NotificationManager.SendNotification::send);
    }

    private NotificationManager.NotificationRequestBuilder declinedRequestNotification(SupplierOrderLine supplierOrderLine) {
        SupplierOrderRequest request = supplierOrderLine.getRequest();

        Product requestedProduct = request.getProduct();
        String productName = requestedProduct.getName();

        String subject = "Fill-Up order for %s declined".formatted(productName);
        String body = "Your fill-up order from %s for %s has been declined.".formatted(
                datatypeFormatter.formatDate(request.getCreatedDate()),
                productName
        );
        return requesterNotification(supplierOrderLine, subject, body);
    }

    private NotificationManager.NotificationRequestBuilder placedOrderNotification(SupplierOrderLine supplierOrderLine) {
        SupplierOrderRequest request = supplierOrderLine.getRequest();

        Product requestedProduct = request.getProduct();
        String productName = requestedProduct.getName();

        String subject = "Order with Fill-Up order placed".formatted(productName);
        String body = "Your fill-up order from %s for '%s' has been approved and an order has been placed.".formatted(
                datatypeFormatter.formatDate(request.getCreatedDate()),
                productName
        );
        return requesterNotification(supplierOrderLine, subject, body);
    }

    private NotificationManager.NotificationRequestBuilder requesterNotification(SupplierOrderLine supplierOrderLine, String subject, String body) {
        User requestedBy = supplierOrderLine.getRequest().getRequestedBy();
        return notificationManager.createNotification()
                .withSubject(subject)
                .withRecipientUsernames(requestedBy.getUsername())
                .toChannelsByNames("in-app")
                .withContentType(ContentType.PLAIN)
                .withBody(body);
    }


    @Override
    public FileRef placeSupplierOrder(SupplierOrder supplierOrder, User reviewedBy) {
        log.info("Placing Supplier Order: {}", supplierOrder);

        SupplierOrder reloadedSupplierOrder = dataManager.load(Id.of(supplierOrder)).one();

        reloadedSupplierOrder.setStatus(SupplierOrderStatus.ORDERED);

        SupplierOrder supplierOrderWithUpdatedStatus = dataManager.save(reloadedSupplierOrder);

        supplierOrder.getOrderLines().stream().map(this::placedOrderNotification)
                .forEach(NotificationManager.SendNotification::send);

        FileRef orderFormFile = createSupplierOrderForm(reviewedBy, reloadedSupplierOrder);

        supplierOrderWithUpdatedStatus.setOrderForm(orderFormFile);

        dataManager.save(supplierOrderWithUpdatedStatus);

        return orderFormFile;
    }

    private FileRef createSupplierOrderForm(User reviewedBy, SupplierOrder reloadedSupplierOrder) {
        ReportOutputDocument document = reportRunner.byReportCode("supplier-order-form")
                .addParam("entity", reloadedSupplierOrder)
                .addParam("reviewedBy", reviewedBy)
                .run();

        ByteArrayInputStream documentBytes = new ByteArrayInputStream(document.getContent());
        return fileStorage.saveStream("supplier-order-form.docx", documentBytes);
    }

}
