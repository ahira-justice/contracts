package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class UnauthenticatedException extends ApplicationException {

    public UnauthenticatedException() {
        super(ErrorConstants.UNAUTHENTICATED, 401);
    }

    public UnauthenticatedException(String error) {
        super(error, 401);
    }

    public UnauthenticatedException(String errorDescription, String error) {
        super(errorDescription, error, 401);
    }

}
