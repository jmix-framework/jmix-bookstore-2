package io.jmix.bookstore.entity;


import io.jmix.core.annotation.TenantId;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@JmixEntity
@MappedSuperclass
public class StandardTenantEntity extends StandardEntity {


    @SystemLevel
    @TenantId
    @Column(name = "TENANT")
    private String tenant;

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

}
