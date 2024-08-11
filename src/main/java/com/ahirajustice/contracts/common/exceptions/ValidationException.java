package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;
import com.ahirajustice.contracts.common.error.Error;
import com.ahirajustice.contracts.common.error.ErrorResponse;
import com.ahirajustice.contracts.common.error.ValidationErrorResponse;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends ApplicationException {

    private final Map<String, String> errors;

    private ValidationException() {
        super("One or more validation errors have occurred", ErrorConstants.UNPROCESSABLE_ENTITY, 422);
        this.errors = new HashMap<>();
    }

    public ValidationException(Error error) {
        this();
        this.errors.put(error.getField(), error.getMessage());
    }

    public ValidationException(List<Error> errors) {
        this();
        errors.forEach(error -> this.errors.put(error.getField(), error.getMessage()));
    }

    @Override
    public ErrorResponse toErrorResponse() {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        errorResponse.setError(getError());
        errorResponse.setErrorDescription(getErrorDescription());
        errorResponse.setErrors(getErrors());

        return errorResponse;
    }

}
