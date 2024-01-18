package io.jmix.bookstore.multitenancy.test_environment.cleanup;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties("bookstore.tenant")
public class TenantCleanupProperties {


    /**
     * tenants with creation date older than n hours will be periodically deleted
     */
    @NotNull
    Integer deleteTenantsOlderThanHours;


    public Integer getDeleteTenantsOlderThanHours() {
        return deleteTenantsOlderThanHours;
    }

    public void setDeleteTenantsOlderThanHours(Integer deleteTenantsOlderThanHours) {
        this.deleteTenantsOlderThanHours = deleteTenantsOlderThanHours;
    }
}
