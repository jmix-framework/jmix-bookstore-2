package io.jmix.bookstore.entity.test_support;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Position;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.Address;
import io.jmix.bookstore.entity.Title;
import io.jmix.bookstore.entity.User;
import io.jmix.core.annotation.Secret;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserData {

    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    Boolean active = true;
    String timeZoneId;
}
