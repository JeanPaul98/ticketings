package com.acl.ticketinggateway.request;

import com.acl.ticketinggateway.dto.PrestationDto;
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
public class PaiementRequestOtp {

    @NotNull
    @NotBlank
    private String countryCode;

    @NotNull
    @NotBlank
    private String codeOtp;

    @NotNull
    @NotBlank
    private String phone;

    private String prestation;

    private double montant;


}
