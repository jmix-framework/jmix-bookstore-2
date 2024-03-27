package io.jmix.bookstore.view.login;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.core.MessageTools;
import io.jmix.core.security.AccessDeniedException;
import io.jmix.flowui.component.loginform.JmixLoginForm;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.loginform.JmixLoginI18n;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.MultitenancyProperties;
import io.jmix.multitenancyflowui.MultitenancyUiSupport;
import io.jmix.securityflowui.authentication.AuthDetails;
import io.jmix.securityflowui.authentication.LoginViewSupport;
import jakarta.servlet.http.Cookie;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

@Route(value = "login")
@ViewController("bookstore_LoginView")
@ViewDescriptor("login-view.xml")
public class LoginView extends StandardView implements LocaleChangeObserver {

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    @Autowired
    private LoginViewSupport loginViewSupport;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private TestEnvironmentTenants testEnvironmentTenants;
    @Autowired
    private MultitenancyProperties multitenancyProperties;
    @Autowired
    private MultitenancyUiSupport multitenancyUiSupport;

    @ViewComponent
    private TypedTextField<Object> tenantField;
    @ViewComponent
    private JmixLoginForm login;

    @Value("${ui.login.defaultUsername:}")
    private String defaultUsername;

    @Value("${ui.login.defaultPassword:}")
    private String defaultPassword;

    private Location currentLocation;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        String tenantId = findOrGenerateTenantId();
        tenantField.setValue(tenantId);
        setTenantCookie(tenantId);
    }

    private String findOrGenerateTenantId() {
        String tenantId = currentLocation.getQueryParameters().getSingleParameter(multitenancyProperties.getTenantIdUrlParamName())
                .orElse(getTenantCookie());
        return tenantId != null ? tenantId : testEnvironmentTenants.generateRandomTenantId();
    }

    private void setTenantCookie(String tenantId) {
        Cookie cookie = new Cookie("tenantId", tenantId);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private String getTenantCookie() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("tenantId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        initLocales();
        initDefaultCredentials();
    }

    protected void initLocales() {
        ComponentUtils.setItemsMap(login,
                MapUtils.invertMap(messageTools.getAvailableLocalesMap()));

        login.setSelectedLocale(VaadinSession.getCurrent().getLocale());
    }

    protected void initDefaultCredentials() {
        if (StringUtils.isNotBlank(defaultUsername)) {
            login.setUsername(defaultUsername);
        }

        if (StringUtils.isNotBlank(defaultPassword)) {
            login.setPassword(defaultPassword);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        currentLocation = event.getLocation();
        super.beforeEnter(event);
    }

    @Subscribe("login")
    public void onLogin(LoginEvent event) {
        String tenantId = tenantField.getValue();
        if (!testEnvironmentTenants.isPresent(tenantId)) {
            testEnvironmentTenants.createTenant(tenantId);
        }
        String username = multitenancyUiSupport.getUsernameByTenant(event.getUsername(), tenantId);
        try {
            loginViewSupport.authenticate(
                    AuthDetails.of(username, event.getPassword())
                            .withLocale(login.getSelectedLocale())
                            .withRememberMe(login.isRememberMe())
            );
        } catch (BadCredentialsException | DisabledException | LockedException | AccessDeniedException e) {
            log.info("Login failed", e);
            event.getSource().setError(true);
        }
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        UI.getCurrent().getPage().setTitle(messageBundle.getMessage("LoginView.title"));

        JmixLoginI18n loginI18n = JmixLoginI18n.createDefault();

        JmixLoginI18n.JmixForm form = new JmixLoginI18n.JmixForm();
        form.setTitle(messageBundle.getMessage("loginForm.headerTitle"));
        form.setUsername(messageBundle.getMessage("loginForm.username"));
        form.setPassword(messageBundle.getMessage("loginForm.password"));
        form.setSubmit(messageBundle.getMessage("loginForm.submit"));
        form.setForgotPassword(messageBundle.getMessage("loginForm.forgotPassword"));
        form.setRememberMe(messageBundle.getMessage("loginForm.rememberMe"));
        loginI18n.setForm(form);

        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle(messageBundle.getMessage("loginForm.errorTitle"));
        errorMessage.setMessage(messageBundle.getMessage("loginForm.badCredentials"));
        loginI18n.setErrorMessage(errorMessage);

        login.setI18n(loginI18n);
    }

    @Subscribe(id = "editTenantBtn", subject = "clickListener")
    public void onEditTenantBtnClick(final ClickEvent<JmixButton> event) {
        tenantField.setReadOnly(false);
    }

    @Subscribe("tenantField")
    public void onTenantFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        setTenantCookie(event.getValue());
    }
}
