package io.jmix.bookstore.security;

import io.jmix.core.JmixSecurityFilterChainOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static io.jmix.bookstore.JmixBookstoreApplication.PERFORMANCE_TESTS_PROFILE;

@Configuration
public class AppSecurityConfiguration {

    @Bean
    @Order(JmixSecurityFilterChainOrder.FLOWUI - 10)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/branding/**")
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/branding/**").permitAll());
        return http.build();
    }

    @Bean
    @Order(JmixSecurityFilterChainOrder.FLOWUI - 15)
    @Profile(PERFORMANCE_TESTS_PROFILE)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/actuator/**")
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/actuator/**").permitAll());
        return http.build();
    }

    @Bean
    @Order(JmixSecurityFilterChainOrder.CUSTOM)
    SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/healthcheck")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                );

        return http.build();
    }
}
