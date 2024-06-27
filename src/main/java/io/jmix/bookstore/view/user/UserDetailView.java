package io.jmix.bookstore.view.user;

import com.google.common.base.Strings;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.view.main.MainView;
import io.jmix.core.EntityStates;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.core.TenantProvider;
import io.jmix.multitenancyflowui.MultitenancyUiSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Route(value = "users/:id", layout = MainView.class)
@ViewController("bookstore_User.detail")
@ViewDescriptor("user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User> {

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @ViewComponent
    private ComboBox<String> timeZoneField;
    @ViewComponent
    private JmixComboBox<String> tenantField;
    @ViewComponent
    private TypedTextField<String> firstNameField;

    @Autowired
    private EntityStates entityStates;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TenantProvider tenantProvider;
    @Autowired
    private MultitenancyUiSupport multitenancyUiSupport;

    @Subscribe
    public void onInit(InitEvent event) {
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
        tenantField.setItems(multitenancyUiSupport.getTenantOptions());
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        tenantField.setReadOnly(false);
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        String currentTenantId = tenantProvider.getCurrentUserTenantId();
        if (!currentTenantId.equals(TenantProvider.NO_TENANT)
                && Strings.isNullOrEmpty(tenantField.getValue())) {
            tenantField.setReadOnly(true);
            tenantField.setValue(currentTenantId);
        }
    }

    @Subscribe(id = "userDc", target = Target.DATA_CONTAINER)
    public void onUserDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<User> event) {
        if (event.getProperty().equals("tenantId")) {
            usernameField.setValue(
                    multitenancyUiSupport.getUsernameByTenant(usernameField.getValue(), (String) event.getValue())
            );
        }
    }

    @Subscribe
    public void onReady(ReadyEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            firstNameField.focus();
        }
    }

    @Subscribe
    public void onValidation(ValidationEvent event) {
        if (entityStates.isNew(getEditedEntity())
                && !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
            event.getErrors().add(messageBundle.getMessage("passwordsDoNotMatch"));
        }
    }

    @Subscribe
    protected void onBeforeSave(BeforeSaveEvent event) {
        User user = getEditedEntity();
        if (entityStates.isNew(user)) {
            user.setUsername(multitenancyUiSupport.getUsernameByTenant(usernameField.getValue(), tenantField.getValue()));
            user.setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }
}