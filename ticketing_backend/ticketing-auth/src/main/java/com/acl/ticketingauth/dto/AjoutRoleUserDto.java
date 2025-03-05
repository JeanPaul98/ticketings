package com.acl.ticketingauth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Zansouyé
 */
@Getter
@Setter
@ToString
public class AjoutRoleUserDto {
    @NotBlank
    @NotNull
    private String code;
}
