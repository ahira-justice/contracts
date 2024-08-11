package com.ahirajustice.contracts.modules.auth.services;

import com.ahirajustice.contracts.modules.auth.dtos.AuthToken;
import com.ahirajustice.contracts.modules.auth.requests.LoginRequest;
import com.ahirajustice.contracts.modules.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    AuthToken decodeJwt(String token);

}
