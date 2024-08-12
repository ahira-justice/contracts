package com.ahirajustice.contracts.modules.client.controllers;

import com.ahirajustice.contracts.modules.client.requests.CreateClientRequest;
import com.ahirajustice.contracts.modules.client.services.ClientService;
import com.ahirajustice.contracts.modules.client.viewmodels.ClientViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ClientViewModel createClient(@Valid @RequestBody CreateClientRequest request) {
        return clientService.createClient(request);
    }

}
