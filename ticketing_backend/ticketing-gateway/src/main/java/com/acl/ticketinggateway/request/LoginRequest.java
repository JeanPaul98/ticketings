package com.acl.ticketinggateway.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Les identifiants de la connexion")
public class LoginRequest {

    private String username;

    private String password;


}
