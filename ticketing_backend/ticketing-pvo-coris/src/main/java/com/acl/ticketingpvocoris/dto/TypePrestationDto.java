package com.acl.ticketingpvocoris.dto;

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

    private String compteAcredite;
}
