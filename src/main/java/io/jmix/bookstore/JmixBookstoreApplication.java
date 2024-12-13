package io.jmix.bookstore;

import com.google.common.base.Strings;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import io.jmix.bookstore.perftests.BookstoreListMenu;
import io.jmix.core.repository.EnableJmixDataRepositories;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import io.jmix.notifications.NotificationType;
import io.jmix.notifications.NotificationTypesRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

@Push
@Theme(value = "jmix-bookstore")
@PWA(name = "Jmix Bookstore", shortName = "Jmix Bookstore")
@EnableAsync
@SpringBootApplication
@EnableJmixDataRepositories
public class JmixBookstoreApplication implements AppShellConfigurator {
    public static final String PERFORMANCE_TESTS_PROFILE = "perf-tests";

    @Autowired
    private Environment environment;

    @Autowired
    private NotificationTypesRepository notificationTypesRepository;

    public static void main(String[] args) {
        SpringApplication.run(JmixBookstoreApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @EventListener
    public void onApplicationContextRefreshed(final ContextRefreshedEvent event) {
        notificationTypesRepository.registerTypes(
                new NotificationType("info", "INFO_CIRCLE"),
                new NotificationType("warn", "WARNING")
        );
    }

    @EventListener
    public void printApplicationUrl(ApplicationStartedEvent event) {
        LoggerFactory.getLogger(JmixBookstoreApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    @Bean
    @Profile(PERFORMANCE_TESTS_PROFILE)
    protected ComponentRegistration bookstoreListMenu() {
        return ComponentRegistrationBuilder.create(BookstoreListMenu.class)
                .replaceComponent(JmixListMenu.class)
                .build();
    }
}
