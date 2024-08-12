package com.ahirajustice.contracts.modules.contractor.controllers;

import com.ahirajustice.contracts.modules.contractor.requests.CreateContractorRequest;
import com.ahirajustice.contracts.modules.contractor.services.ContractorService;
import com.ahirajustice.contracts.modules.contractor.viewmodels.ContractorViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/contractors")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ContractorViewModel createContractor(@Valid @RequestBody CreateContractorRequest request) {
        return contractorService.createContractor(request);
    }

}
