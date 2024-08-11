package com.ahirajustice.contracts.common.exceptions;

import com.ahirajustice.contracts.common.constants.ErrorConstants;

public class ConfigurationException extends ApplicationException {

    public ConfigurationException() {
        super("Invalid system configuration", ErrorConstants.NOT_IMPLEMENTED, 501);
    }

    public ConfigurationException(String errorDescription) {
        super(errorDescription, ErrorConstants.NOT_IMPLEMENTED, 501);
    }

}
