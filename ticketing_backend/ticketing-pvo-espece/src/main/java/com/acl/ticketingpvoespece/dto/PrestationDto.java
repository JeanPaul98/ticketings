package com.acl.ticketingpvoespece.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrestationDto {

    @NotNull
    @NotBlank
    private String designation;


}
