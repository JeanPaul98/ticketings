package com.acl.ticketingauth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Zansouyé
 */
@Getter
@Setter
public class RoleDto {

    @NotBlank
    @NotNull
    private String code;

    @NotBlank
    @NotNull
    private String roleDesc;

    private boolean actif;
}
