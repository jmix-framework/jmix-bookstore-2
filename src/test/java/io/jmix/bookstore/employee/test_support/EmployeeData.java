package io.jmix.bookstore.employee.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.entity.User;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeData {
    String firstName;
    String lastName;
    Title title;
    Address address;
    LocalDate birthDate;
    LocalDate hireDate;
    LocalDate probationEndDate;
    Employee reportsTo;
    List<Territory> territories;
    String notes;
    User user;
    Position position;

}
