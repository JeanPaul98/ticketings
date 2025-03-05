package com.acl.ticketingpvoespece.dto;

import lombok.*;

import java.util.Date;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTicketValidationDto {

    private String codeTicket;

    private Date expirationTime;
}