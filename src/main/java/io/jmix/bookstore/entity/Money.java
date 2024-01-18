package io.jmix.bookstore.entity;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@JmixEntity(name = "bookstore_Money")
@Embeddable
public class Money {
    @NotNull
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    public Currency getCurrency() {
        return currency == null ? null : Currency.fromId(currency);
    }

    public void setCurrency(Currency currency) {
        this.currency = currency == null ? null : currency.getId();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @InstanceName
    @DependsOnProperties({"amount", "currency"})
    public String getInstanceName(Messages messages) {
        return String.format("%s %s", amount, messages.getMessage(getCurrency()));
    }
}
