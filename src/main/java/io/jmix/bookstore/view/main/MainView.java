package io.jmix.bookstore.view.main;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.PositionTranslation;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.bookstore.security.session.BookstoreSessionData;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.core.TenantProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Route("")
@ViewController("bookstore_MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private TenantProvider tenantProvider;
    @Autowired
    private TestEnvironmentTenants testEnvironmentTenants;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private BookstoreSessionData bookstoreSessionData;

    @ViewComponent
    private Span tenantLabel;
    @ViewComponent
    private Span positionLabel;
    @ViewComponent
    private Avatar userAvatar;
    @ViewComponent
    private JmixImage<Object> userAvatarImage;
    @ViewComponent
    private H2 welcomeMessage;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        tenantLabel.setText(tenantProvider.getCurrentUserTenantId());
        initLayout();
        initTenantTestdataIfNecessary();
    }

    private void initTenantTestdataIfNecessary() {
        String tenantId = tenantProvider.getCurrentUserTenantId();
        if (testEnvironmentTenants.isPresent(tenantId) && !testEnvironmentTenants.hasGeneratedTestdata(tenantId)) {
            dialogs.createBackgroundTaskDialog(new BackgroundTask<Number, Boolean>(10_000) {
                        @Override
                        public Boolean run(TaskLifeCycle<Number> taskLifeCycle) {
                            try {
                                taskLifeCycle.publish();
                                testEnvironmentTenants.generateRandomTestdata(tenantId);
                            }
                            catch (Exception e) {
                                return false;
                            }
                            return true;
                        }

                        @Override
                        public void done(Boolean result) {
                            if (Boolean.TRUE.equals(result)) {
                                notifications.create(messageBundle.getMessage("testdataCreated"))
                                        .withPosition(Notification.Position.BOTTOM_END)
                                        .show();
                            }
                            else {
                                notifications.create(messageBundle.getMessage("testdataNotCreatedCaption"), messageBundle.getMessage("testdataNotCreatedDescription"))
                                        .withType(Notifications.Type.ERROR)
                                        .show();
                            }
                        }
                    })
                    .withHeader(messageBundle.getMessage("createTestdataCaption"))
                    .withText(messageBundle.formatMessage("createTestdataMessage", tenantId))
                    .withShowProgressInPercentage(false)
                    .withCancelAllowed(false)
                    .open();
        }
    }

    private void initLayout() {
        String username = currentUser().getUsername().replaceAll(currentTenant()+"\\|", "");
        userAvatar.setImage("avatars/%s.png".formatted(username));
        userAvatarImage.setSrc("avatars/%s.png".formatted(username));

        welcomeMessage.setText(messageBundle.formatMessage("welcomeMessageUser", currentUser().getFirstName()));
        bookstoreSessionData.employee()
                .ifPresent(it -> {
                    positionLabel.setText(translatedPosition(it));
                    String colorStyleName = it.getPosition().getColor().getStyleName();
                    positionLabel.setClassName(colorStyleName);
                    userAvatar.setClassName("user-avatar user-avatar-border-" + colorStyleName);
                    userAvatarImage.setClassName("user-avatar user-avatar-border-" + colorStyleName);
                });

    }

    private String translatedPosition(Employee employee) {
        Locale currentLocale = currentAuthentication.getLocale();

        String localisedPositionName = employee.getPosition().getTranslations().stream()
                .filter(positionTranslation -> positionTranslation.getLocale().equals(currentLocale))
                .map(PositionTranslation::getName)
                .findFirst()
                .orElse(employee.getPosition().getName());

        return localisedPositionName;
    }

    private User currentUser() {
        return (User) currentAuthentication.getUser();
    }

    private String currentTenant() {
        return tenantProvider.getCurrentUserTenantId();
    }
}
