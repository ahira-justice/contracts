package com.ahirajustice.contracts.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String AUTH_HEADER_STRING = "Authorization";
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] {
            "/**, OPTIONS",
            "/api/auth/token, POST",
    };

}
