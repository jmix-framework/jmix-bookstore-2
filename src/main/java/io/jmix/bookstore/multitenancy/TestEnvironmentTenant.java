package io.jmix.bookstore.multitenancy;

import io.jmix.core.entity.annotation.ReplaceEntity;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.multitenancy.entity.Tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.OffsetDateTime;

@JmixEntity
@Entity
@ReplaceEntity(Tenant.class)
public class TestEnvironmentTenant extends Tenant {

    @Column(name = "LAST_LOGIN")
    private OffsetDateTime lastLogin;

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    @Column(name = "TESTDATA_INITIALISED")
    private Boolean testdataInitialised;

    public Boolean getTestdataInitialised() {
        return testdataInitialised;
    }

    public void setTestdataInitialised(Boolean testdataInitialised) {
        this.testdataInitialised = testdataInitialised;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
