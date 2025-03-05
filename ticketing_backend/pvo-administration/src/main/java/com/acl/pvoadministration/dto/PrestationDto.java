package com.acl.pvoadministration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestationDto {

    @NotNull
    @NotBlank
    private String designation;

    private boolean personnePhysique;

}
