package com.acl.ticketinggateway.request;

import com.acl.ticketinggateway.dto.AjoutRoleUserDto;
import com.acl.ticketinggateway.dto.RoleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String  nom;

    @NotNull
    @NotBlank
    private String prenom;

    @NotNull
    @NotBlank
    private String telephone;

    private Set<AjoutRoleUserDto> roleDtoSet;
}
