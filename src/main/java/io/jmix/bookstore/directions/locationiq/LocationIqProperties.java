package io.jmix.bookstore.directions.locationiq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties("bookstore.locationiq")
public class LocationIqProperties {

    /**
     * the base url of the LocationIQ API
     */
    @NotNull
    @NotEmpty
    String baseUrl;

    /**
     * the API key of the Location IQ API
     */
    @NotNull
    @NotEmpty
    String apiKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
