package io.jmix.bookstore.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import static com.google.common.base.Strings.nullToEmpty;

@Embeddable
@JmixEntity(name = "bookstore_Address")
public class Address {
    @Column(name = "STREET")
    private String street;

    @Column(name = "POST_CODE")
    private String postCode;

    @Column(name = "CITY")
    private String city;

    @Geometry
    @PropertyDatatype("geoPoint")
    @Column(name = "POSITION_")
    private Point position;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @InstanceName
    @DependsOnProperties({"street", "postCode", "city"})
    public String getInstanceName() {
        return String.format("%s\n%s %s", nullToEmpty(street), nullToEmpty(postCode), nullToEmpty(city));
    }
}
