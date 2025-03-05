package com.acl.ticketinggateway.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequest {

    @NotNull
    @NotBlank
    private String countryCode;

    @NotNull
    @NotBlank
    private String phone;


}
