package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class NotFoundException extends ApplicationException {

    public NotFoundException() {
        super(ErrorConstants.NOT_FOUND, 404);
    }

    public NotFoundException(String errorDescription) {
        super(errorDescription, ErrorConstants.NOT_FOUND, 404);
    }

}
