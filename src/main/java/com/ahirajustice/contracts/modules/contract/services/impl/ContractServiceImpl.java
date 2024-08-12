package com.ahirajustice.contracts.modules.contract.services.impl;

import com.ahirajustice.contracts.common.entities.Client;
import com.ahirajustice.contracts.common.entities.Contract;
import com.ahirajustice.contracts.common.entities.Contractor;
import com.ahirajustice.contracts.common.enums.ContractStatus;
import com.ahirajustice.contracts.common.repositories.ContractRepository;
import com.ahirajustice.contracts.modules.client.services.ClientService;
import com.ahirajustice.contracts.modules.contract.requests.CreateContractRequest;
import com.ahirajustice.contracts.modules.contract.services.ContractService;
import com.ahirajustice.contracts.modules.contract.viewmodels.ContractViewModel;
import com.ahirajustice.contracts.modules.contractor.services.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractorService contractorService;
    private final ClientService clientService;

    @Override
    public ContractViewModel createContract(CreateContractRequest request) {
        Contractor contractor = contractorService.validateContractor(request.getContractor());
        Client client = clientService.validateClient(request.getClient());

        Contract contract = persistContract(request, contractor, client);
        return ContractViewModel.from(contract);
    }

    private Contract persistContract(CreateContractRequest request, Contractor contractor, Client client) {
        Contract contract = buildContract(request, contractor, client);
        return contractRepository.save(contract);
    }

    private Contract buildContract(CreateContractRequest request, Contractor contractor, Client client) {
        return Contract.builder()
                .terms(request.getTerms())
                .status(ContractStatus.NEW)
                .contractor(contractor)
                .client(client)
                .build();
    }

}
