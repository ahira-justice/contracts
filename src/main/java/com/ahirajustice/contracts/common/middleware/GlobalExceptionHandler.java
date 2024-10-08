package com.ahirajustice.contracts.common.middleware;

import com.ahirajustice.contracts.common.error.Error;
import com.ahirajustice.contracts.common.error.ErrorResponse;
import com.ahirajustice.contracts.common.exceptions.ApplicationException;
import com.ahirajustice.contracts.common.exceptions.ForbiddenException;
import com.ahirajustice.contracts.common.exceptions.NotFoundException;
import com.ahirajustice.contracts.common.exceptions.ServerErrorException;
import com.ahirajustice.contracts.common.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        if (ex instanceof ApplicationException) {
            return handleApplicationException((ApplicationException) ex);
        }
        else if (ex instanceof NoHandlerFoundException) {
            return handleNoHandlerFoundException((NoHandlerFoundException) ex);
        }
        else if (ex instanceof AccessDeniedException) {
            return handleAccessDeniedException();
        }
        else if (ex instanceof BindException) {
            return handleBindException((BindException) ex);
        }
        else {
            return handleUnknownException();
        }
    }

    private ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), HttpStatus.valueOf(ex.getStatusCode()));
    }

    private ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return handleApplicationException(new NotFoundException(String.format("Route %s %s does not exist", ex.getHttpMethod(), ex.getRequestURL())));
    }

    private ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        return handleApplicationException(new ForbiddenException());
    }

    private ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        List<Error> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Error.create(error.getField(), error.getDefaultMessage(), error.getRejectedValue()))
                .collect(Collectors.toList());

        return handleApplicationException(new ValidationException(errors));
    }

    private ResponseEntity<ErrorResponse> handleUnknownException() {
        return handleApplicationException(new ServerErrorException());
    }
}
