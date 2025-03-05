package com.acl.pvoadministration.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@Getter
@Setter
public class TicketValidationResponse {
    private String codeTicket;
    private Date expirationTime;
    private boolean isValid;
}
