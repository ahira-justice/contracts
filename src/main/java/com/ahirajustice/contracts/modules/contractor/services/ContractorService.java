package com.ahirajustice.contracts.modules.contractor.services;

import com.ahirajustice.contracts.modules.contractor.requests.CreateContractorRequest;
import com.ahirajustice.contracts.modules.contractor.viewmodels.ContractorViewModel;

public interface ContractorService {

    ContractorViewModel createContractor(CreateContractorRequest request);

}
