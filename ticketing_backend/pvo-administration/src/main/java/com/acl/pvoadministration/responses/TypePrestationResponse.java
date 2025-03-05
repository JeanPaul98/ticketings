package com.acl.pvoadministration.responses;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TypePrestationResponse implements Serializable {
    private Long id;
    private String designation;
    private String compteAcredite;
    private double prixUnitaire;
}
