package io.jmix.bookstore.fulfillment.test_support;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.entity.Address;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.locationtech.jts.geom.Polygon;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FulfillmentCenterData {
    String name;
    Address address;
    Region region;
}
