package com.ahirajustice.contracts.common.services.impl;

import com.ahirajustice.contracts.common.constants.SecurityConstants;
import com.ahirajustice.contracts.common.services.LoggedInService;
import com.ahirajustice.contracts.modules.auth.dtos.AuthToken;
import com.ahirajustice.contracts.modules.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoggedInServiceImpl implements LoggedInService {

    private final HttpServletRequest request;
    private final AuthService authService;

    @Override
    public Optional<String> getUsername() {
        AuthToken authToken = decodeAuthToken();

        if (authToken == null)
            return Optional.empty();

        return Optional.ofNullable(authToken.getUsername());
    }

    private AuthToken decodeAuthToken() {
        String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER_STRING);

        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        String token = authHeader.split(" ")[1];
        return authService.decodeJwt(token);
    }

}
