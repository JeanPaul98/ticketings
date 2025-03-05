package com.acl.pvoadministration.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportCorisModel {
    private String codeTicket;
    private String montant;
    private String reference;
    private String telephone;
    private String datePaiement;

    public ReportCorisModel(String codeTicket, String montant, String reference, String telephone, String datePaiement) {
        this.codeTicket = codeTicket;
        this.montant = montant;
        this.reference = reference;
        this.telephone = telephone;
        this.datePaiement = datePaiement;
    }
}
