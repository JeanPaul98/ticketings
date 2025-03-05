package com.acl.ticketingpvocoris.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDto {

    @NotNull
    @NotBlank
    private String countryCode;

    @NotNull
    @NotBlank
    private String phone;


}
