package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.*;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@JmixEntity
@Table(name = "BOOKSTORE_EMPLOYEE", indexes = {
        @Index(name = "IDX_BOOKSTOREEMPLOYE_REPORTSTO", columnList = "REPORTS_TO_ID"),
        @Index(name = "IDX_BOOKSTORE_EMPLOYEE_USER", columnList = "USER_ID"),
        @Index(name = "IDX_BOOKSTORE_EMPLOYEE_POSITION", columnList = "POSITION_ID")
})
@Entity(name = "bookstore_Employee")
public class Employee extends StandardTenantEntity {
    @InstanceName
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BIRTH_DATE", nullable = false)
    @NotNull
    private LocalDate birthDate;

    @Column(name = "HIRE_DATE", nullable = false)
    @NotNull
    private LocalDate hireDate;

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

    @Column(name = "NOTES")
    @Lob
    private String notes;

    @JoinColumn(name = "REPORTS_TO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee reportsTo;

    @JoinTable(name = "BOOKSTORE_EMPLOYEE_TERRITORIES",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TERRITORY_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Territory> territories;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @JoinColumn(name = "POSITION_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Position position;

    @Column(name = "PROBATION_END_DATE")
    private LocalDate probationEndDate;

    public LocalDate getProbationEndDate() {
        return probationEndDate;
    }

    public void setProbationEndDate(LocalDate probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Title getTitle() {
        return title == null ? null : Title.fromId(title);
    }

    public void setTitle(Title title) {
        this.title = title == null ? null : title.getId();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
