package com.ahirajustice.contracts.modules.contractor.services.impl;

import com.ahirajustice.contracts.common.constants.RoleConstants;
import com.ahirajustice.contracts.common.entities.Account;
import com.ahirajustice.contracts.common.entities.Contractor;
import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.exceptions.NotFoundException;
import com.ahirajustice.contracts.common.repositories.ContractorRepository;
import com.ahirajustice.contracts.modules.account.requests.CreateAccountRequest;
import com.ahirajustice.contracts.modules.account.services.AccountService;
import com.ahirajustice.contracts.modules.contractor.requests.CreateContractorRequest;
import com.ahirajustice.contracts.modules.contractor.services.ContractorService;
import com.ahirajustice.contracts.modules.contractor.viewmodels.ContractorViewModel;
import com.ahirajustice.contracts.modules.user.requests.CreateUserRequest;
import com.ahirajustice.contracts.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final UserService userService;
    private final AccountService accountService;

    @Transactional
    @Override
    public ContractorViewModel createContractor(CreateContractorRequest request) {
        CreateUserRequest createUserRequest = buildCreateUserRequest(request);
        User user = userService.createUser(createUserRequest, RoleConstants.CONTRACTOR);

        CreateAccountRequest createAccountRequest = buildCreateAccountRequest(request);
        Account account = accountService.createAccount(createAccountRequest);

        Contractor contractor = persistContractor(request, user, account);
        return ContractorViewModel.from(contractor);
    }

    private CreateUserRequest buildCreateUserRequest(CreateContractorRequest request) {
        return CreateUserRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    private CreateAccountRequest buildCreateAccountRequest(CreateContractorRequest request) {
        return CreateAccountRequest.builder()
                .accountName(request.getFirstName() + " " + request.getLastName())
                .currencyCode(request.getCurrencyCode())
                .build();
    }

    private Contractor persistContractor(CreateContractorRequest request, User user, Account account) {
        Contractor contractor = buildContractor(request, user, account);
        return contractorRepository.save(contractor);
    }

    private Contractor buildContractor(CreateContractorRequest request, User user, Account account) {
        return Contractor.builder()
                .profession(request.getProfession())
                .user(user)
                .account(account)
                .build();
    }

    @Override
    public Contractor validateContractor(String username){
        Contractor contractor = contractorRepository.findByUsername(username).orElse(null);

        if (contractor == null)
            throw new NotFoundException(String.format("Contractor with username: %s does not exist", username));

        return contractor;
    }

}
