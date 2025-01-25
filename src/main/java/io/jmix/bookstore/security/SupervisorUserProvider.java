package io.jmix.bookstore.security;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.entity.User;
import io.jmix.bpm.provider.UserListProvider;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.FetchPlan;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@UserProvider(
        value = "bookstore_SupervisorUserProvider",
        description = "Returns the manager of a given user"
)
public class SupervisorUserProvider {

    private final UnconstrainedDataManager dataManager;

    @Autowired
    public SupervisorUserProvider(UnconstrainedDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @SuppressWarnings("unused")
    public String find(User reviewedBy) {


        Optional<Employee> reviewedByEmployee = dataManager.load(Employee.class)
                .condition(PropertyCondition.equal("user", reviewedBy))
                .fetchPlan(emFp -> {
                    emFp.addFetchPlan(FetchPlan.BASE);
                    emFp.add("reportsTo", managerEmployeeFp -> {
                        managerEmployeeFp.addFetchPlan(FetchPlan.BASE);
                        managerEmployeeFp.add("user", FetchPlan.BASE);
                    });
                })
                .optional();

        if (reviewedByEmployee.isPresent()) {
            Employee employee = reviewedByEmployee.get();
            if (employee.getReportsTo() != null)
                return employee.getReportsTo().getUser().getUsername();
        }

        return "";
    }
}
