package io.jmix.bookstore.product;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@JmixEntity
@Table(name = "BOOKSTORE_PRODUCT_CATEGORY", indexes = {
        @Index(name = "IDX_BOOKSTORE_PRODUCT_CATEGORY_TENANT", columnList = "TENANT")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_BOOKSTORE_PRODUCT_CATEGORY_UNQ", columnNames = {"NAME", "TENANT"})
})
@Entity(name = "bookstore_ProductCategory")
public class ProductCategory extends StandardTenantEntity {

    @InstanceName
    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
