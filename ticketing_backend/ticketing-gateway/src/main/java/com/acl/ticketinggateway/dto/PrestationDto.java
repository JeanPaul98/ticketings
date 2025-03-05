package com.acl.ticketinggateway.dto;

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
public class PrestationDto {

    @NotNull
    @NotBlank
    private String designation;

//    private boolean personnePhysique;

}
