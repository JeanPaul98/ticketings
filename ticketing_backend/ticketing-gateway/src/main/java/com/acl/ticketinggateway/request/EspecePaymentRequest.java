package com.acl.ticketinggateway.request;

import com.acl.ticketinggateway.dto.PrestationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EspecePaymentRequest {

//    @NotNull
//    @NotBlank
//    private String countryCode;
//
//    @NotNull
//    @NotBlank
//    private String phone;

    @NotNull
    @NotBlank
    private String designation;
}
