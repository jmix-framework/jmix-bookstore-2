package io.jmix.bookstore.entity.datatypes;

import io.jmix.core.LocaleResolver;
import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import org.springframework.lang.Nullable;
import java.text.ParseException;
import java.util.Locale;

@DatatypeDef(
        id = "locale",
        javaClass = Locale.class,
        defaultForClass = true
)
@Ddl("varchar(255)")
public class LocaleDatatype implements Datatype<Locale> {

    @Override
    public String format(@Nullable Object value) {
        if (value instanceof Locale) {
            return LocaleResolver.localeToString((Locale) value);
        }
        return null;
    }

    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value) throws ParseException {
        if (value == null)
            return null;

        try {
            return LocaleResolver.resolve(value);
        } catch (Exception e) {
            throw new ParseException(String.format("Cannot parse %s as Locale: %s", value, e.toString()), 0);
        }
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }
}
