package com.acl.ticketingpvoespece.enums;

public enum TypePaiement {
    ESPECE("ESPECE"),
    MOBILE("MOBILE");

    private String value;
    TypePaiement(String value) {
        this.value = value;
    }
}
