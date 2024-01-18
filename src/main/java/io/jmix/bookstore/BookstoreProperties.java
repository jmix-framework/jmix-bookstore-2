package io.jmix.bookstore;

import io.jmix.bookstore.entity.Currency;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
public class BookstoreProperties {

    @NotNull
    Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
