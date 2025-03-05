package com.acl.pvoadministration.model;

import com.acl.pvoadministration.enums.TypePaiement;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticketing_Paiement", schema = "sipedba")
@ApiModel(value = "Paiement")
public class Paiement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_Paiement_seq")
    @SequenceGenerator(sequenceName = "ticketing_Paiement_seq", allocationSize = 1, name = "ticketing_Paiement_seq")
    private Long id;

    @Column(name = "CODE_TICKET",unique = true, nullable = false)
    private String codeTicket;

    @Column(nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_PAIEMENT")
    private TypePaiement typePaiement;

    @Column(nullable = false)
    private String reference;

    @Column(name = "TRANSACTION_ID",nullable = false)
    private String transactionId;

    private String telephone;

    @Column(nullable = false)
    private String operateur;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_EXPIRATION")
    private Date dateExpiration;

    @ManyToOne
    @JoinColumn(name = "TICKETING_TYPE_PRESTATION_ID")
    private TypePrestation typePrestation;

    @ManyToOne
    @JoinColumn(name = "ticketing_user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
        return Objects.equals(id, paiement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
