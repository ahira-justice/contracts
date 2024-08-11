package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class InvalidOperationException extends ApplicationException {

    public InvalidOperationException(String errorDescription) {
        super(errorDescription, ErrorConstants.INVALID_OPERATION, 400);
    }

}
