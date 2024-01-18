package io.jmix.bookstore.test_support;

import io.jmix.core.validation.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.Set;

@Component
public class EntityValidation {

    @Autowired
    protected Validator validator;

    public <T> void ensureIsValid(T entity) {
        Set<ConstraintViolation<T>> violations = validate(entity);
        if (!violations.isEmpty()) {
            throw new EntityValidationException("Entity validation failed: ", violations);
        }
    }

    public <T> Set<ConstraintViolation<T>> validate(T entity) {
        return validator.validate(entity, Default.class);
    }

}
