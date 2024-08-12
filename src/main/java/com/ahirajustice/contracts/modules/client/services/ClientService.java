package com.ahirajustice.contracts.modules.client.services;

import com.ahirajustice.contracts.modules.client.requests.CreateClientRequest;
import com.ahirajustice.contracts.modules.client.viewmodels.ClientViewModel;

public interface ClientService {

    ClientViewModel createClient(CreateClientRequest request);

}
