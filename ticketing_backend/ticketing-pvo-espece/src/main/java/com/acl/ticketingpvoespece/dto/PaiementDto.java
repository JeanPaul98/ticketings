package com.acl.ticketingpvoespece.dto;

import com.acl.ticketingpvoespece.enums.TypePaiement;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDto {

    private String codeTicket;

    private double montant;

    private String reference;

    private TypePaiement typePaiement;

    private Date dateExpiration;

    private String username;

}
