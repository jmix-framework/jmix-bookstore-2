package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;

@JmixEntity
@Table(name = "BOOKSTORE_POSITION_TRANSLATION", indexes = {
        @Index(name = "IDX_BOOKSTORE_POSITION_TRANSLATION_POSITION", columnList = "POSITION_ID"),
        @Index(name = "IDX_BOOKSTORE_POSITION_TRANSLATION_TENANT", columnList = "TENANT")
})
@Entity(name = "bookstore_PositionTranslation")
public class PositionTranslation extends StandardTenantEntity {
    @JoinColumn(name = "POSITION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Position position;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCALE")
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
