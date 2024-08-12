package com.ahirajustice.contracts.modules.account.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "accountName is required")
    private String accountName;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

}
