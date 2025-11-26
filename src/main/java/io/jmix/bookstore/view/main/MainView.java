package io.jmix.bookstore.view.main;

import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.bookstore.employee.Employee;
import io.jmix.bookstore.employee.PositionTranslation;
import io.jmix.bookstore.entity.User;
import io.jmix.bookstore.multitenancy.TestEnvironmentTenants;
import io.jmix.bookstore.security.session.BookstoreSessionData;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.multitenancy.BpmTenantProvider;
import io.jmix.bpm.service.BpmTaskService;
import io.jmix.bpm.service.UserGroupService;
import io.jmix.bpmflowui.view.mytasks.MyTasksListView;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.core.TenantProvider;
import org.flowable.task.api.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.jmix.bookstore.JmixBookstoreApplication.PERFORMANCE_TESTS_PROFILE;

@Route("")
@ViewController("bookstore_MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    private static final Logger log = LoggerFactory.getLogger(MainView.class);

    @Autowired
    private TenantProvider tenantProvider;
    @Autowired
    private TestEnvironmentTenants testEnvironmentTenants;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private BookstoreSessionData bookstoreSessionData;
    @Autowired
    private BpmTaskService bpmTaskService;
    @Autowired(required = false)
    protected BpmTenantProvider bpmTenantProvider;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Environment environment;
    @Autowired
    private UiComponents uiComponents;

    @ViewComponent
    private Span tenantLabel;
    @ViewComponent
    private JmixImage<Object> userAvatarImage;
    @ViewComponent
    private H2 welcomeMessage;
    @ViewComponent
    private JmixButton taskBtn;
    @ViewComponent
    private Timer taskNotificationTimer;

    private String currentUserName;
    private List<String> userGroupCodes;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        currentUserName = currentUserSubstitution.getEffectiveUser().getUsername();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        tenantLabel.setText(tenantProvider.getCurrentUserTenantId());
        initLayout();
        initTenantTestdataIfNecessary();
        updateTaskCounter();

        if (Arrays.asList(environment.getActiveProfiles()).contains(PERFORMANCE_TESTS_PROFILE)) {
            log.debug("Application is run in performance testing mode. 'taskNotificationTimer' will be disabled");
            taskNotificationTimer.stop();
        }
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
                            } catch (Exception e) {
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
                            } else {
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
        User user = currentUser();
        userAvatarImage.setSrc(getAvatarUrl(user));

        welcomeMessage.setText(messageBundle.formatMessage("welcomeMessageUser", user.getFirstName()));
        employee().ifPresent(it -> {
            String colorStyleName = it.getPosition().getColor().getStyleName();
            userAvatarImage.setClassName("user-avatar user-avatar-border-" + colorStyleName);
        });

    }

    private String translatedPosition(Employee employee) {
        Locale currentLocale = currentAuthentication.getLocale();

        return employee.getPosition().getTranslations().stream()
                .filter(positionTranslation -> positionTranslation.getLocale().equals(currentLocale))
                .map(PositionTranslation::getName)
                .findFirst()
                .orElse(employee.getPosition().getName());
    }

    private User currentUser() {
        return (User) currentAuthentication.getUser();
    }

    private String currentTenant() {
        return tenantProvider.getCurrentUserTenantId();
    }

    private Optional<Employee> employee() {
        return bookstoreSessionData.employee();
    }

    @Subscribe(id = "taskBtn", subject = "clickListener")
    public void onTaskBtnClick(final ClickEvent<JmixButton> event) {
        DialogWindow<MyTasksListView> dialogWindow = dialogWindows.view(this, MyTasksListView.class).build();
        dialogWindow.setHeight("80%");
        dialogWindow.setWidth("80%");
        dialogWindow.open();
    }

    @Subscribe("taskNotificationTimer")
    public void onTaskNotificationTimerTimerAction(final Timer.TimerActionEvent event) {
        updateTaskCounter();
    }

    private void updateTaskCounter() {
        int myTaskCount = bpmTaskService.createTaskQuery()
                .taskAssignee(currentUserName)
                .active()
                .list().size();

        TaskQuery candidatesTaskQuery = bpmTaskService.createTaskQuery();
        if (bpmTenantProvider != null && bpmTenantProvider.isMultitenancyActive()) {
            candidatesTaskQuery.taskTenantId(bpmTenantProvider.getCurrentUserTenantId());
        }
        if (!getUserGroupCodes().isEmpty()) {
            candidatesTaskQuery.taskCandidateGroupIn(getUserGroupCodes());
        }
        candidatesTaskQuery.taskCandidateUser(currentUserName);
        int allGroupTasksCount = candidatesTaskQuery
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list().size();

        int taskCount = myTaskCount + allGroupTasksCount;
        taskBtn.setText("%s".formatted(taskCount));
        if (taskCount > 0) {
            taskBtn.removeClassName("button-counter-empty");
        } else {
            taskBtn.addClassName("button-counter-empty");
        }
    }

    protected List<String> getUserGroupCodes() {
        if (userGroupCodes == null) {
            userGroupCodes = userGroupService.getUserGroups(currentUserName).stream()
                    .map(UserGroup::getCode)
                    .collect(Collectors.toList());
        }
        return userGroupCodes;
    }

    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        String userName = generateUserName(user);

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-button-content");

        Avatar avatar = createAvatar(userName, user);

        Span name = uiComponents.create(Span.class);
        name.setText(userName);
        name.setClassName("user-menu-text");

        content.add(avatar, name);

        return content;
    }

    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content");

        String name = generateUserName(user);

        Avatar avatar = createAvatar(name, user);
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);

        Span text = uiComponents.create(Span.class);
        text.setText(name);
        text.setClassName("user-menu-text");

        content.add(avatar, text);

        employee().ifPresent(it -> {
            String colorStyleName = it.getPosition().getColor().getStyleName();
            Span position = uiComponents.create(Span.class);
            position.setText(translatedPosition(it));
            position.addClassNames("user-menu-subtext", colorStyleName);
            position.getElement().getThemeList().add("badge");

            content.add(position);
        });

        return content;
    }

    private Avatar createAvatar(String fullName, User user) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1");
        avatar.setClassName("user-menu-avatar");
        avatar.setImage(getAvatarUrl(user));

        employee().ifPresent(it -> {
            String colorStyleName = it.getPosition().getColor().getStyleName();
            avatar.addClassNames("user-avatar", "user-avatar-border-" + colorStyleName);
        });

        return avatar;
    }

    private String getAvatarUrl(User user) {
        String username = getOriginalUsername(user);
        return "avatars/%s.png".formatted(username);
    }

    private String generateUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        return userName.isEmpty() ? getOriginalUsername(user) : userName;
    }

    private String getOriginalUsername(User user) {
        return user.getUsername().replaceAll(currentTenant() + "\\|", "");
    }
}
