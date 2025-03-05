package com.acl.pvoadministration.playload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @Column(name="LOGUSER")
    @NotBlank
    private String username;

    @Column(name="LOGPASS")
    @NotBlank
    private String password;

}
