package com.acl.pvoadministration.dto;

import com.acl.pvoadministration.enums.TypePaiement;
import com.acl.pvoadministration.model.Paiement;
import com.acl.pvoadministration.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PaiementDto {

    private String codeTicket;

    private double montant;

    private TypePaiement typePaiement;

    private String reference;

    private String transactionId;

    private String telephone;

    private String operateur;

    private Date dateExpiration;

    private User user;

    private Date createdAt;

    public PaiementDto(Paiement paiement) {
    }
}
