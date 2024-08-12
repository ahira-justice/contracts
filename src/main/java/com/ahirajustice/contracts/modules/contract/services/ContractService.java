package com.ahirajustice.contracts.modules.contract.services;

import com.ahirajustice.contracts.modules.contract.requests.CreateContractRequest;
import com.ahirajustice.contracts.modules.contract.viewmodels.ContractViewModel;

public interface ContractService {

    ContractViewModel createContract(CreateContractRequest request);

}
