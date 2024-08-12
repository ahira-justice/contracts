package com.ahirajustice.contracts.modules.client.requests;

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
public class CreateClientRequest {

    @NotBlank(message = "profession is required")
    private String profession;
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;

}
