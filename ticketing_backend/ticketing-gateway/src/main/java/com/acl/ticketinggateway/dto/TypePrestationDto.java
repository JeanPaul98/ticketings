package com.acl.ticketinggateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypePrestationDto {

    @NotNull
    @NotBlank
    private String designation;

    private double prixUnitaire;

    private String compteAcredite;

    private String prestation;
}
