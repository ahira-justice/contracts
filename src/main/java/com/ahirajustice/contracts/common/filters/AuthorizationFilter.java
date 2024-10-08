package com.ahirajustice.contracts.common.filters;

import com.ahirajustice.contracts.common.constants.SecurityConstants;
import com.ahirajustice.contracts.common.error.ErrorResponse;
import com.ahirajustice.contracts.common.exceptions.UnauthenticatedException;
import com.ahirajustice.contracts.common.repositories.UserRepository;
import com.ahirajustice.contracts.common.utils.ObjectMapperUtils;
import com.ahirajustice.contracts.common.utils.SecurityUtils;
import com.ahirajustice.contracts.modules.auth.dtos.AuthToken;
import com.ahirajustice.contracts.modules.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Order(1)
public class AuthorizationFilter extends GenericFilterBean {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        else {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Max-Age", "10");
        }

        if (!excludeFromAuth(request.getRequestURI(), request.getMethod())) {
            String header = request.getHeader(SecurityConstants.AUTH_HEADER_STRING);

            if (StringUtils.isBlank(header)) {
                writeErrorToResponse("Missing authorization header", response);
                return;
            }

            String scheme = header.split(" ")[0];
            String token = header.split(" ")[1];

            if (StringUtils.isBlank(scheme) || StringUtils.isBlank(token)) {
                writeErrorToResponse("Malformed authorization header", response);
                return;
            }

            if (!StringUtils.lowerCase(scheme).equals("bearer")) {
                writeErrorToResponse("Invalid authentication scheme", response);
                return;
            }

            AuthToken authToken = authService.decodeJwt(token);

            if (!userExists(authToken) || isExpired(authToken)) {
                writeErrorToResponse("Invalid or expired token", response);
                return;
            }

            Authentication authentication = getAuthentication(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(AuthToken authToken) {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority(String.format("ROLE_%s", authToken.getRole())));
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException { }

            @Override
            public String getName() {
                return authToken.getUsername();
            }
        };
    }

    private boolean excludeFromAuth(String requestUri, String requestMethod) {
        for (String uriMethodCsv : SecurityConstants.EXCLUDE_FROM_AUTH_URLS) {
            if (!SecurityUtils.uriMatch(requestUri, requestMethod, uriMethodCsv))
                continue;

            return true;
        }

        return false;
    }

    private boolean userExists(AuthToken token) {
        return userRepository.findByUsername(token.getUsername()).isPresent();
    }

    private boolean isExpired(AuthToken token) {
        return Instant.now().isAfter(token.getExpiry().toInstant());
    }

    private void writeErrorToResponse(String message, HttpServletResponse response) throws IOException {
        UnauthenticatedException ex = new UnauthenticatedException(message);
        ErrorResponse errorResponse = ex.toErrorResponse();

        String errorResponseBody = ObjectMapperUtils.serialize(errorResponse);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ex.getStatusCode());
        writer.print(errorResponseBody);
        writer.flush();
    }
}
