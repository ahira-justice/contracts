package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super(ErrorConstants.FORBIDDEN, 403);
    }

    public ForbiddenException(String username) {
        super(
                String.format("%s is not allowed to access this resource or perform this action", username),
                ErrorConstants.FORBIDDEN,
                403
        );
    }

}
