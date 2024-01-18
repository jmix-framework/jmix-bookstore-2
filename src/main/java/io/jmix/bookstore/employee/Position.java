package io.jmix.bookstore.employee;

import io.jmix.bookstore.entity.StandardTenantEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.security.CurrentAuthentication;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

@JmixEntity
@Table(name = "BOOKSTORE_POSITION")
@Entity(name = "bookstore_Position")
public class Position extends StandardTenantEntity {

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "COLOR")
    private String color;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "position")
    private List<PositionTranslation> translations;

    public List<PositionTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<PositionTranslation> translations) {
        this.translations = translations;
    }

    public PositionColor getColor() {
        return color == null ? null : PositionColor.fromId(color);
    }

    public void setColor(PositionColor color) {
        this.color = color == null ? null : color.getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @InstanceName
    @DependsOnProperties({"name", "translations"})
    public String getInstanceName(CurrentAuthentication currentAuthentication) {
        Locale currentLocale = currentAuthentication.getLocale();
        return translations.stream().filter(positionTranslation -> positionTranslation.getLocale().equals(currentLocale))
                .map(PositionTranslation::getName)
                .findFirst()
                .orElse(getName());
    }
}
