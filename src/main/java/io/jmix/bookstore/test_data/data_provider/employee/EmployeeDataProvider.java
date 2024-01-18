package io.jmix.bookstore.test_data.data_provider.employee;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.security.EmployeeRole;
import io.jmix.bookstore.security.UiMinimalRole;
import io.jmix.bookstore.test_data.AddressGenerator;
import io.jmix.bpmflowui.security.role.BpmProcessActorRole;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.reportsflowui.role.ReportsRunRole;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import net.datafaker.DateAndTime;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("bookstore_EmployeeDataProvider")
public class EmployeeDataProvider {

    protected final DataManager dataManager;
    protected final PasswordEncoder passwordEncoder;
    protected final AddressGenerator addressGenerator;

    public EmployeeDataProvider(DataManager dataManager, PasswordEncoder passwordEncoder, AddressGenerator addressGenerator) {
        this.dataManager = dataManager;
        this.passwordEncoder = passwordEncoder;
        this.addressGenerator = addressGenerator;
    }

    private static LocalDate randomBirthday() {
        DateAndTime date = new Faker().date();
        return date.birthday().toLocalDateTime().toLocalDate();
    }

    public List<Employee> save(List<EmployeeData> employeeData) {
        return findEmployeesOf(commit(toEmployeesWithUser(employeeData)));
    }

    public List<Object> toEmployeesWithUser(List<EmployeeData> employeeData) {
        return employeeData.stream()
                .flatMap(this::toEmployeeWithUser)
                .collect(Collectors.toList());
    }

    public Stream<Object> toEmployeeWithUser(EmployeeData employeeData) {
        User user = dataManager.create(User.class);

        user.setFirstName(employeeData.firstName());
        user.setLastName(employeeData.lastName());
        user.setUsername("%s|%s".formatted(employeeData.tenantId(), employeeData.username()));
        user.setPassword(passwordEncoder.encode(employeeData.username()));
        user.setEmail("%s@jmix-bookstore.com".formatted(employeeData.username()));
        user.setAssociatedRegions(employeeData.associatedRegions());


        Employee employee = dataManager.create(Employee.class);

        employee.setUser(user);
        employee.setFirstName(employeeData.firstName());
        employee.setLastName(employeeData.lastName());
        employee.setPosition(employeeData.position());
        employee.setHireDate(employeeData.hireDate());
        employee.setProbationEndDate(employeeData.hireDate().plusMonths(6));
        employee.setTitle(employeeData.title());
        employee.setReportsTo(employeeData.manager());

        employee.setBirthDate(randomBirthday());
        employee.setAddress(addressGenerator.generate());

        employee.setTerritories(employeeData.territories().stream().toList());


        Stream<RoleAssignmentEntity> resourceRoleAssignments =
                Stream.concat(employeeData.resourceRoleCodes().stream(), baseEmployeeRoles())
                        .map(roleCode -> toRoleAssignment(user, roleCode, RoleAssignmentRoleType.RESOURCE));

        Stream<RoleAssignmentEntity> rowLevelRoleAssignments =
                employeeData.rowLevelRoleCodes().stream()
                        .map(roleCode -> toRoleAssignment(user, roleCode, RoleAssignmentRoleType.ROW_LEVEL));

        return Stream.concat(
                Stream.concat(resourceRoleAssignments, rowLevelRoleAssignments),
                Stream.of(user, employee)
        );
    }

    private Stream<String> baseEmployeeRoles() {
        return Stream.of(
                UiMinimalRole.CODE,
                BpmProcessActorRole.CODE,
                ReportsRunRole.CODE,
                "notifications-in-app-notifications-user",
                EmployeeRole.CODE
        );
    }


    private RoleAssignmentEntity toRoleAssignment(User user, String roleCode, String roleType) {
        RoleAssignmentEntity userRole = dataManager.create(RoleAssignmentEntity.class);
        userRole.setUsername(user.getUsername());
        userRole.setRoleType(roleType);
        userRole.setRoleCode(roleCode);
        return userRole;
    }

    private <T> List<T> commit(List<T> entities) {
        SaveContext saveContext = new SaveContext();
        saveContext.setDiscardSaved(true);
        entities.forEach(saveContext::saving);
        dataManager.save(saveContext);

        return entities;
    }

    private List<Employee> findEmployeesOf(List<Object> storedEntities) {
        return storedEntities.stream()
                .filter(o -> o instanceof Employee)
                .map(user -> (Employee) user)
                .collect(Collectors.toList());
    }

}
