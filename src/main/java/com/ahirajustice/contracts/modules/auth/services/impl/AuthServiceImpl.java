package com.ahirajustice.contracts.modules.auth.services.impl;

import com.ahirajustice.contracts.common.constants.SecurityConstants;
import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.enums.TimeFactor;
import com.ahirajustice.contracts.common.exceptions.ConfigurationException;
import com.ahirajustice.contracts.common.exceptions.UnauthenticatedException;
import com.ahirajustice.contracts.common.properties.AppProperties;
import com.ahirajustice.contracts.common.repositories.UserRepository;
import com.ahirajustice.contracts.common.utils.CommonUtils;
import com.ahirajustice.contracts.modules.auth.dtos.AuthToken;
import com.ahirajustice.contracts.modules.auth.requests.LoginRequest;
import com.ahirajustice.contracts.modules.auth.responses.LoginResponse;
import com.ahirajustice.contracts.modules.auth.services.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = authenticateUser(request.getUsername(), request.getPassword());
        return generateAuthToken(user, request.getExpires());
    }

    private User authenticateUser(String username, String password) {
        Optional<User> userExists = userRepository.findByUsername(username);

        if (userExists.isEmpty())
            throw new UnauthenticatedException("Incorrect username or password");

        if (!verifyPassword(password, userExists.get().getPassword()))
            throw new UnauthenticatedException("Incorrect username or password");

        return userExists.get();
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    private LoginResponse generateAuthToken(User user, int expires) {
        int expiry = expires > 0 ? expires : appProperties.getAccessTokenExpireMinutes();

        PrivateKey privateKey = getPrivateKey();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + CommonUtils.convertToMillis(expiry, TimeFactor.MINUTE)))
                .claim("role", user.getRole())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    private PrivateKey getPrivateKey() {
        PKCS8EncodedKeySpec keySpec;
        KeyFactory kf;
        PrivateKey privateKey;

        try {
            byte [] privateKeyBytes = Base64.getDecoder().decode(appProperties.getPrivateKey());
            keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ConfigurationException(ex.getMessage());
        }

        return privateKey;
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        PublicKey publicKey = getPublicKey();

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setExpiry(claims.getExpiration());
            authToken.setRole(claims.get("role", String.class));
        }
        catch(ExpiredJwtException | MalformedJwtException ex){
            return authToken;
        }

        return authToken;
    }

    private PublicKey getPublicKey() {
        X509EncodedKeySpec keySpec;
        KeyFactory kf;
        PublicKey publicKey;

        try {
            byte [] publicKeyBytes = Base64.getDecoder().decode(appProperties.getPublicKey());
            keySpec = new X509EncodedKeySpec(publicKeyBytes);
            kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ConfigurationException(ex.getMessage());
        }

        return publicKey;
    }

}
