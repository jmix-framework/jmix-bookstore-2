package io.jmix.bookstore.entity.datatypes;

import io.jmix.core.LocaleResolver;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Locale;

@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale locale) {
        if (locale == null)
            return null;

        return LocaleResolver.localeToString(locale);
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return LocaleResolver.resolve(dbData);
    }
}
