package com.acl.ticketinggateway.playload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {
    @NotNull
    @NotBlank
    private String countryCode;
    @NotNull
    @NotBlank
    private String phone;
}
