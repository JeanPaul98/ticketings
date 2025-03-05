package com.acl.ticketinggateway.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "LoginRequestDto")
public class LoginRequestDto {
    private String username;

    private String password;
    /**
     * CODE : ABXY=TPE, PJOZ=WEB
     *
     */
    private String code;
}
