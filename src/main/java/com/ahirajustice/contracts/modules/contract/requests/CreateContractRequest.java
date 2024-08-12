package com.ahirajustice.contracts.modules.contract.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractRequest {

    private String terms;
    private String contractor;
    private String client;

}
