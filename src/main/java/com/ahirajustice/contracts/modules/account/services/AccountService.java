package com.ahirajustice.contracts.modules.account.services;

import com.ahirajustice.contracts.common.entities.Account;
import com.ahirajustice.contracts.modules.account.requests.CreateAccountRequest;

public interface AccountService {

    Account createAccount(CreateAccountRequest request);

}
