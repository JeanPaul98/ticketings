package com.acl.ticketinggateway.playload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class CorisPaymentRequest extends UserRequest {
    @NotNull
    @NotBlank
    private String amount;
    @NotNull
    @NotBlank
    private String codeOTP;
    @NotNull
    @Min(1)
    private Long prestationId;
    @NotNull
    @NotBlank
    private String typePaiement;
}
