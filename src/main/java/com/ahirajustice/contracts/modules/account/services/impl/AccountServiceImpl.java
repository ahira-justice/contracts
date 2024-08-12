package com.ahirajustice.contracts.modules.account.services.impl;

import com.ahirajustice.contracts.common.constants.ErrorConstants;
import com.ahirajustice.contracts.common.entities.Account;
import com.ahirajustice.contracts.common.exceptions.BadRequestException;
import com.ahirajustice.contracts.common.properties.AppProperties;
import com.ahirajustice.contracts.common.repositories.AccountRepository;
import com.ahirajustice.contracts.common.utils.CommonUtils;
import com.ahirajustice.contracts.modules.account.requests.CreateAccountRequest;
import com.ahirajustice.contracts.modules.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AppProperties properties;
    
    @Override
    public Account createAccount(CreateAccountRequest request) {
        validateAccountCreationRequest(request);
        return persistAccount(request);
    }
    
    private void validateAccountCreationRequest(CreateAccountRequest request) {
        validateCurrencyCode(request.getCurrencyCode());
    }

    private void validateCurrencyCode(String currencyCode) {
        if (!properties.getSupportedCurrencyCodes().contains(currencyCode))
            throw new BadRequestException("Invalid currency code", ErrorConstants.INVALID_CURRENCY);
    }

    private Account persistAccount(CreateAccountRequest request) {
        Account account = buildAccount(request);
        return accountRepository.save(account);
    }

    private Account buildAccount(CreateAccountRequest request) {
        return Account.builder()
                .accountName(request.getAccountName())
                .accountNumber(generateAccountNumber())
                .balance(0L)
                .balanceComputedOn(LocalDateTime.now())
                .currencyCode(request.getCurrencyCode())
                .build();
    }

    private String generateAccountNumber() {
        String accountNumber;

        do {
            accountNumber = CommonUtils.generateRandomDigits(properties.getAccountNumberLength());
        }
        while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

}
