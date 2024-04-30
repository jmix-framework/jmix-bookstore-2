package io.jmix.bookstore.notification;

import io.jmix.core.DataManager;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookstore_NotificationCenter")
public class NotificationCenter {
    private static final Logger log = LoggerFactory.getLogger(NotificationCenter.class);
    protected final NotificationManager notificationManager;
    protected final RoleAssignmentRepository roleAssignmentRepository;
    protected final DataManager dataManager;
    protected final List<NotificationDetailDataProvider> notificationDetailDataProviders;

    public NotificationCenter(
            NotificationManager notificationManager,
            RoleAssignmentRepository roleAssignmentRepository,
            DataManager dataManager,
            List<NotificationDetailDataProvider> notificationDetailDataProviders
    ) {
        this.notificationManager = notificationManager;
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.dataManager = dataManager;
        this.notificationDetailDataProviders = notificationDetailDataProviders;
    }


    @EventListener
    public void onInAppNotificationSourceEvent(InAppNotificationSource event) {

        log.info("Retrieved In-App Notification Event. Creating Notifications for: {}", event);

        notificationDetailDataProviders
                .stream()
                .filter(notificationDetailDataProvider -> notificationDetailDataProvider.supports(event))
                .findFirst()
                .map(it -> provideData(it, event))
                .ifPresentOrElse(
                        this::sendNotification,
                        () -> log.warn("No Notification Data Provider found for {}", event)
                );

    }

    private void sendNotification(NotificationDetailDataProvider.NotificationDetailData detailData) {
        notificationManager.createNotification()
                .withSubject(detailData.subject())
                .withRecipientUsernames(detailData.recipients())
                .toChannelsByNames("in-app")
                .withContentType(ContentType.PLAIN)
                .withBody(detailData.body())
                .send();
    }

    private static <T extends InAppNotificationSource> NotificationDetailDataProvider.NotificationDetailData provideData(NotificationDetailDataProvider<T> it, T event) {
        log.info(
                "Notification Data Provider found: {} to handle event: {}",
                it.getClass().getSimpleName(),
                event
        );
        return it.provideData(event);
    }
}
