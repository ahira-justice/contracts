package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class ServerErrorException extends ApplicationException {

    public ServerErrorException() {
        super(
                "An unexpected error occurred. Please try again or confirm server operation status",
                ErrorConstants.SERVER_ERROR,
                500
        );
    }

    public ServerErrorException(String errorDescription) {
        super(errorDescription, ErrorConstants.SERVER_ERROR, 500);
    }

}
