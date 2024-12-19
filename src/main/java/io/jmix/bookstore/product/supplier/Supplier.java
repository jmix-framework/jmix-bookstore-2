package io.jmix.bookstore.product.supplier;

import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.bookstore.entity.Title;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@JmixEntity
@Table(name = "BOOKSTORE_SUPPLIER", indexes = {
        @Index(name = "IDX_BOOKSTORE_SUPPLIER_TENANT", columnList = "TENANT")
})
@Entity(name = "bookstore_Supplier")
public class Supplier extends StandardTenantEntity {
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

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

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_TITLE")
    private String contactTitle;

    @Column(name = "PHONE")
    private String phone;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEBSITE")
    private String website;


    @NotNull
    @Column(name = "COOPERATION_STATUS", nullable = false)
    private String cooperationStatus;

    public CooperationStatus getCooperationStatus() {
        return cooperationStatus == null ? null : CooperationStatus.fromId(cooperationStatus);
    }

    public void setCooperationStatus(CooperationStatus coorperationStatus) {
        this.cooperationStatus = coorperationStatus == null ? null : coorperationStatus.getId();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Title getContactTitle() {
        return contactTitle == null ? null : Title.fromId(contactTitle);
    }

    public void setContactTitle(Title contactTitle) {
        this.contactTitle = contactTitle == null ? null : contactTitle.getId();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
