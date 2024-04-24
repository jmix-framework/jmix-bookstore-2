package io.jmix.bookstore.product.supplier.test_support;

import io.jmix.bookstore.customer.test_support.Customers;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.test_support.AddressData;
import io.jmix.bookstore.entity.test_support.AddressMapper;
import io.jmix.bookstore.product.supplier.SupplierOrder;
import io.jmix.bookstore.product.supplier.SupplierOrderStatus;
import io.jmix.bookstore.test_support.TestDataProvisioning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SupplierOrders
        implements TestDataProvisioning<SupplierOrderData, SupplierOrderData.SupplierOrderDataBuilder, SupplierOrder> {

    private static final String DEFAULT_SHIPPING_ADDRESS_POST_CODE = "12345";
    private static final String DEFAULT_SHIPPING_ADDRESS_CITY = "City";
    private static final String DEFAULT_SHIPPING_ADDRESS_STREET = "Street";
    private static final SupplierOrderStatus DEFAULT_SUPPLIER_ORDER_STATUS = SupplierOrderStatus.APPROVED;
    @Autowired
    SupplierOrderRepository supplierOrderRepository;
    @Autowired @Lazy
    SupplierOrderLines supplierOrderLines;
    @Autowired
    Customers customers;
    @Autowired
    private SupplierOrderMapper supplierOrderMapper;
    @Autowired
    private AddressMapper addressMapper;
    public static final LocalDate DEFAULT_ORDER_DATE = LocalDate.now().plusDays(1);


    @Override
    public SupplierOrderData.SupplierOrderDataBuilder defaultData() {
        return SupplierOrderData.builder()
                .orderDate(DEFAULT_ORDER_DATE)
                .status(DEFAULT_SUPPLIER_ORDER_STATUS)
                .customer(customers.createDefault())
                .shippingAddress(defaultShippingAddress())
                .supplierOrderLines(List.of());
    }

    private Address defaultShippingAddress() {
        return addressMapper.toEntity(
                AddressData.builder()
                        .city(DEFAULT_SHIPPING_ADDRESS_CITY)
                        .street(DEFAULT_SHIPPING_ADDRESS_STREET)
                        .postCode(DEFAULT_SHIPPING_ADDRESS_POST_CODE)
                        .build()
        );
    }

    @Override
    public SupplierOrder save(SupplierOrderData supplierOrderData)  {
        return supplierOrderRepository.save(supplierOrderData);
    }

    @Override
    public SupplierOrder create(SupplierOrderData supplierOrderData) {
        return supplierOrderMapper.toEntity(supplierOrderData);
    }

    @Override
    public SupplierOrder createDefault() {
        return create(defaultData().build());
    }

    @Override
    public SupplierOrder saveDefault() {
        return save(defaultData().build());
    }

}
