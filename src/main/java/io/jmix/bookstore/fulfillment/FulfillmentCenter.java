package io.jmix.bookstore.fulfillment;

import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_FULFILLMENT_CENTER", indexes = {
        @Index(name = "IDX_BOOKSTORE_FULFILLMENT_CENTER_REGION", columnList = "REGION_ID")
})
@Entity(name = "bookstore_FulfillmentCenter")
public class FulfillmentCenter extends StandardTenantEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;


    @Geometry
    @JmixProperty
    public Point getGeometry() {
        return address != null? address.getPosition() : null;
    }

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "ADDRESS_STREET")),
            @AttributeOverride(name = "postCode", column = @Column(name = "ADDRESS_POST_CODE")),
            @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY")),
            @AttributeOverride(name = "position", column = @Column(name = "ADDRESS_POSITION_")),
            @AttributeOverride(name = "state", column = @Column(name = "ADDRESS_STATE")),
            @AttributeOverride(name = "country", column = @Column(name = "ADDRESS_COUNTRY"))
    })
    private Address address;

    @NotNull
    @JoinColumn(name = "REGION_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
