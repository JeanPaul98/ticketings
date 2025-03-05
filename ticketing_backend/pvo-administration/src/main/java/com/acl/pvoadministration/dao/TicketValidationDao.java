package com.acl.pvoadministration.dao;

import lombok.*;

import java.util.Date;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketValidationDao {
    private String codeTicket;
    private Date expirationTime;
    private boolean status;
}
