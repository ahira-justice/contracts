package com.ahirajustice.contracts.common.utils;

import com.ahirajustice.contracts.common.exceptions.ConfigurationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {

    public static PublicKey getPublicKey(String publicKeyString) {
        X509EncodedKeySpec keySpec;
        KeyFactory kf;
        PublicKey publicKey;

        try {
            byte [] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            keySpec = new X509EncodedKeySpec(publicKeyBytes);
            kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ConfigurationException(ex.getMessage());
        }

        return publicKey;
    }

}
