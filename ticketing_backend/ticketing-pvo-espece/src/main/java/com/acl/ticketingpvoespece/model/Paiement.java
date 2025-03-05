package com.acl.ticketingpvoespece.model;

import com.acl.ticketingpvoespece.enums.TypePaiement;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ticketing_Paiement", schema = "sipedba")
@Schema(description = "Paiement")
public class Paiement implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_Paiement_seq")
    @SequenceGenerator(sequenceName = "ticketing_Paiement_seq", allocationSize = 1, name = "ticketing_Paiement_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE_TICKET",unique = true)
    private String codeTicket;

    private double montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_PAIEMENT")
    private TypePaiement typePaiement;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(name ="transaction_id" ,nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String operateur;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_EXPIRATION")
    private Date dateExpiration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "ticketing_type_prestation_id")
    private TypePrestation typePrestation;

    @ManyToOne
    @JoinColumn(name = "ticketing_user_id")
    private User user;

    public Paiement(String codeTicket, String reference,double montant,String transactionId,
                    String operateur, String telephone,Date dateExpiration,Date createdAt,
                    TypePaiement typePaiement, User user){
        this.codeTicket = codeTicket;
        this.reference = reference;
        this.montant = montant;
        this.transactionId = transactionId;
        this.operateur = operateur;
        this.telephone = telephone;
        this.dateExpiration = dateExpiration;
        this.createdAt = createdAt;
        this.typePaiement = typePaiement;
        this.user = user;
    }
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
