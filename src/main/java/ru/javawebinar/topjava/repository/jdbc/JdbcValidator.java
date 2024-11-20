package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class JdbcValidator {

    private final Validator validator;

    public JdbcValidator() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> void validate(T model) {
        Set<ConstraintViolation<T>> violationResult = validator.validate(model);
        if (!violationResult.isEmpty()) {
            throw new ConstraintViolationException(violationResult);
        }
    }
}
