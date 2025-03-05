package com.acl.ticketingpvocoris.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckOtpResponse {

    private String message;

    private String code;

    private String transactionId;

    private double montant;
}
