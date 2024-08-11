package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class UpstreamServerException extends ApplicationException {

    public UpstreamServerException() {
        super("An error occurred while attempting to reach upstream server", ErrorConstants.BAD_GATEWAY, 502);
    }

    public UpstreamServerException(String errorDescription) {
        super(errorDescription, ErrorConstants.BAD_GATEWAY, 502);
    }

}
