package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_REGION", indexes = {
        @Index(name = "IDX_BOOKSTORE_REGION_TENANT", columnList = "TENANT")
})
@Entity(name = "bookstore_Region")
public class Region extends StandardTenantEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
