package com.ahirajustice.contracts.modules.contract.controllers;

import com.ahirajustice.contracts.modules.contract.requests.CreateContractRequest;
import com.ahirajustice.contracts.modules.contract.services.ContractService;
import com.ahirajustice.contracts.modules.contract.viewmodels.ContractViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ahirajustice.contracts.common.constants.RoleConstants.AUTH_PREFIX;
import static com.ahirajustice.contracts.common.constants.RoleConstants.AUTH_SUFFIX;
import static com.ahirajustice.contracts.common.constants.RoleConstants.SUPER_ADMIN;

@RestController
@RequestMapping("api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PreAuthorize(AUTH_PREFIX + SUPER_ADMIN + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ContractViewModel createContract(@Valid @RequestBody CreateContractRequest request) {
        return contractService.createContract(request);
    }

}
