package com.acl.pvoadministration.responses;

import com.acl.pvoadministration.enums.TypePaiement;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class PaiementResponse {

    private String codeTicket;

    private double montant;

    private TypePaiement typePaiement;

    private Date createdAt;
}
