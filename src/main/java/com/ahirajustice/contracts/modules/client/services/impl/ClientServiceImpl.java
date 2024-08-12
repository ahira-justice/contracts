package com.ahirajustice.contracts.modules.client.services.impl;

import com.ahirajustice.contracts.common.constants.RoleConstants;
import com.ahirajustice.contracts.common.entities.Account;
import com.ahirajustice.contracts.common.entities.Client;
import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.repositories.ClientRepository;
import com.ahirajustice.contracts.modules.account.requests.CreateAccountRequest;
import com.ahirajustice.contracts.modules.account.services.AccountService;
import com.ahirajustice.contracts.modules.client.requests.CreateClientRequest;
import com.ahirajustice.contracts.modules.client.services.ClientService;
import com.ahirajustice.contracts.modules.client.viewmodels.ClientViewModel;
import com.ahirajustice.contracts.modules.user.requests.CreateUserRequest;
import com.ahirajustice.contracts.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;
    private final AccountService accountService;

    @Transactional
    @Override
    public ClientViewModel createClient(CreateClientRequest request) {
        CreateUserRequest createUserRequest = buildCreateUserRequest(request);
        User user = userService.createUser(createUserRequest, RoleConstants.CLIENT);

        CreateAccountRequest createAccountRequest = buildCreateAccountRequest(request);
        Account account = accountService.createAccount(createAccountRequest);

        Client client = persistClient(request, user, account);
        return ClientViewModel.from(client);
    }

    private CreateUserRequest buildCreateUserRequest(CreateClientRequest request) {
        return CreateUserRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    private CreateAccountRequest buildCreateAccountRequest(CreateClientRequest request) {
        return CreateAccountRequest.builder()
                .accountName(request.getFirstName() + " " + request.getLastName())
                .currencyCode(request.getCurrencyCode())
                .build();
    }

    private Client persistClient(CreateClientRequest request, User user, Account account) {
        Client client = buildClient(request, user, account);
        return clientRepository.save(client);
    }

    private Client buildClient(CreateClientRequest request, User user, Account account) {
        return Client.builder()
                .profession(request.getProfession())
                .user(user)
                .account(account)
                .build();
    }

}
