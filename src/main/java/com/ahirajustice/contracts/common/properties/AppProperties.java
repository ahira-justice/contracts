package com.ahirajustice.contracts.common.properties;

import com.ahirajustice.contracts.common.exceptions.ConfigurationException;
import com.ahirajustice.contracts.common.models.Currency;
import com.ahirajustice.contracts.common.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Component
public class AppProperties {

    public AppProperties(@Value("classpath:currencies.json") Resource supportedCurrenciesConfig) {
        List<Currency> currencies = ObjectMapperUtils.deserialize(supportedCurrenciesConfig, new TypeReference<>() {});
        validateSupportedCurrenciesConfig(currencies);
        this.supportedCurrencies = currencies;
        this.supportedCurrencyCodes = currencies.stream().map(Currency::getCurrencyCode).collect(Collectors.toList());
    }

    private void validateSupportedCurrenciesConfig(List<Currency> currencies) {
        try {
            currencies.forEach(currency -> java.util.Currency.getInstance(currency.getCurrencyCode()));
        }
        catch (IllegalArgumentException ex) {
            var message = "Invalid configuration for supported currency codes. " +
                    "Configured currency codes must be ISO 4217 compliant. " +
                    "See https://www.iso.org/iso-4217-currency-codes.html";
            throw new ConfigurationException(message);
        }
    }

    private final List<Currency> supportedCurrencies;

    private final List<String> supportedCurrencyCodes;

    @Value("${app.config.access-token-expire-minutes}")
    private int accessTokenExpireMinutes;
    @Value("${app.config.public-key}")
    private String publicKey;
    @Value("${app.config.private-key}")
    private String privateKey;
    @Value("${app.config.superuser.email}")
    private String superuserEmail;
    @Value("${app.config.superuser.username}")
    private String superuserUsername;
    @Value("${app.config.superuser.first-name}")
    private String superuserFirstName;
    @Value("${app.config.superuser.last-name}")
    private String superuserLastName;
    @Value("${app.config.superuser.password}")
    private String superuserPassword;
    @Value("${app.config.account.account-number.length}")
    private int accountNumberLength;

}
