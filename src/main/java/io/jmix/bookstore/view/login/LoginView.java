package io.jmix.bookstore.view.login;

import com.google.common.base.Strings;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.core.CoreProperties;
import io.jmix.core.MessageTools;
import io.jmix.core.security.AccessDeniedException;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.MultitenancyProperties;
import io.jmix.multitenancyflowui.MultitenancyUiSupport;
import io.jmix.securityflowui.authentication.AuthDetails;
import io.jmix.securityflowui.authentication.LoginViewSupport;
import jakarta.servlet.http.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Route(value = "login")
@ViewController("bookstore_LoginView")
@ViewDescriptor("login-view.xml")
public class LoginView extends StandardView implements LocaleChangeObserver {

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private JmixPasswordField passwordField;
    @ViewComponent
    private JmixCheckbox rememberMe;
    @ViewComponent
    private JmixSelect<Locale> localeSelect;
    @ViewComponent
    private JmixButton submitBtn;
    @ViewComponent
    private Div errorMessage;
    @ViewComponent
    private H5 errorMessageTitle;
    @ViewComponent
    private Paragraph errorMessageDescription;
    @ViewComponent
    private TypedTextField<Object> tenantField;
    @ViewComponent
    private JmixButton possibleUsersBtn;

    @Autowired
    private Dialogs dialogs;
    @Autowired
    private CoreProperties coreProperties;
    @Autowired
    private LoginViewSupport loginViewSupport;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private TestEnvironmentTenants testEnvironmentTenants;
    @Autowired
    private MultitenancyProperties multitenancyProperties;
    @Autowired
    private MultitenancyUiSupport multitenancyUiSupport;

    @Value("${ui.login.defaultUsername:}")
    private String defaultUsername;

    @Value("${ui.login.defaultPassword:}")
    private String defaultPassword;

    private Location currentLocation;

    @Subscribe
    public void onInit(final InitEvent event) {
        initLocales();
        initDefaultCredentials();
        // TODO replace with action when https://github.com/jmix-framework/jmix/issues/3213 is fixed
        submitBtn.addClickShortcut(Key.ENTER);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        String tenantId = findOrGenerateTenantId();
        tenantField.setValue(tenantId);
        setTenantCookie(tenantId);
    }

    protected void initLocales() {
        LinkedHashMap<Locale, String> locales = coreProperties.getAvailableLocales().stream()
                .collect(Collectors.toMap(Function.identity(), messageTools::getLocaleDisplayName, (s1, s2) -> s1,
                        LinkedHashMap::new));

        ComponentUtils.setItemsMap(localeSelect, locales);

        localeSelect.setValue(VaadinSession.getCurrent().getLocale());
    }

    @Subscribe("localeSelect")
    public void onLocaleSelectComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<Locale>, Locale> event) {
        Locale locale = event.getValue();
        VaadinSession.getCurrent().setLocale(locale);
    }

    protected void initDefaultCredentials() {
        if (StringUtils.isNotBlank(defaultUsername)) {
            usernameField.setTypedValue(defaultUsername);
        }

        if (StringUtils.isNotBlank(defaultPassword)) {
            passwordField.setValue(defaultPassword);
        }
    }

    @Subscribe(id = "submitBtn", subject = "clickListener")
    public void onSubmitBtnClick(final ClickEvent<JmixButton> event) {
        errorMessage.setVisible(false);

        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            return;
        }

        String tenantId = tenantField.getValue();
        if (!testEnvironmentTenants.isPresent(tenantId)) {
            testEnvironmentTenants.createTenant(tenantId);
        }
        username = multitenancyUiSupport.getUsernameByTenant(username, tenantId);

        try {
            loginViewSupport.authenticate(
                    AuthDetails.of(username, password)
                            .withLocale(localeSelect.getValue())
                            .withRememberMe(rememberMe.getValue())
            );
        } catch (final BadCredentialsException | DisabledException | LockedException | AccessDeniedException e) {
            log.warn("Login failed for user '{}': {}", username, e.toString());

            errorMessageTitle.setText(messageBundle.getMessage("loginForm.errorTitle"));
            errorMessageDescription.setText(messageBundle.getMessage("loginForm.badCredentials"));
            errorMessage.setVisible(true);
        }
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        currentLocation = event.getLocation();
        super.beforeEnter(event);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        UI.getCurrent().getPage().setTitle(messageBundle.getMessage("LoginView.title"));

        usernameField.setLabel(messageBundle.getMessage("loginForm.username"));
        passwordField.setLabel(messageBundle.getMessage("loginForm.password"));
        rememberMe.setLabel(messageBundle.getMessage("loginForm.rememberMe"));
        submitBtn.setText(messageBundle.getMessage("loginForm.submit"));

        tenantField.setLabel(messageBundle.getMessage("tenant"));
        possibleUsersBtn.setText(messageBundle.getMessage("possibleUsersHelp"));
    }

    @Subscribe(id = "editTenantBtn", subject = "clickListener")
    public void onEditTenantBtnClick(final ClickEvent<JmixButton> event) {
        tenantField.setReadOnly(false);
    }

    @Subscribe("tenantField")
    public void onTenantFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        setTenantCookie(event.getValue());
    }

    @Subscribe(id = "possibleUsersBtn", subject = "clickListener")
    public void onPossibleUsersBtnClick(final ClickEvent<JmixButton> event) {
        dialogs.createMessageDialog()
                .withHeader(messageBundle.getMessage("possibleUsersLoginDialogCaption"))
                .withContent(new Html(messageBundle.getMessage("possibleUsersDialogMessage")))
                .open();
    }

    @Subscribe(id = "helpTenantBtn", subject = "clickListener")
    public void onHelpTenantBtnClick(final ClickEvent<JmixButton> event) {
        dialogs.createMessageDialog()
                .withContent(new Html(messageBundle.getMessage("tenantFieldHelpText")))
                .open();
    }
}
