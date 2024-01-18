package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Polygon;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_TERRITORY", indexes = {
        @Index(name = "IDX_BOOKSTORE_TERRITORY_REGION", columnList = "REGION_ID")
})
@Entity(name = "bookstore_Territory")
public class Territory extends StandardTenantEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "REGION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;

    @JoinTable(name = "BOOKSTORE_EMPLOYEE_TERRITORIES",
            joinColumns = @JoinColumn(name = "TERRITORY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Employee> employees;


    @Geometry
    @PropertyDatatype("geoPolygon")
    @Column(name = "GEOGRAPHICAL_AREA")
    private Polygon geographicalArea;

    public void setGeographicalArea(Polygon geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    public Polygon getGeographicalArea() {
        return geographicalArea;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

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
}
