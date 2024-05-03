package io.jmix.bookstore.employee.listener;

import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.Region;
import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeTerritoriesChangeListener {
    private final DataManager dataManager;

    public EmployeeTerritoriesChangeListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @EventListener
    public void onEmployeeChangedBeforeCommit(EntityChangedEvent<Employee> event) {

        if (event.getType() != EntityChangedEvent.Type.DELETED
                && event.getChanges().isChanged("territories")) {
            Employee employee = dataManager.load(event.getEntityId()).one();

            User user = employee.getUser();
            user.setAssociatedRegions(
                    associatedRegionsFromTerritories(employee)
            );

            dataManager.save(user);
        }
    }

    private Set<Region> associatedRegionsFromTerritories(Employee employee) {
        return employee.getTerritories()
                .stream()
                .map(Territory::getRegion)
                .collect(Collectors.toSet());
    }
}
