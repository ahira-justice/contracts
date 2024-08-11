package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String errorDescription) {
        super(errorDescription, ErrorConstants.BAD_REQUEST, 400);
    }

    public BadRequestException(String errorDescription, String error) {
        super(errorDescription, error, 400);
    }

}
