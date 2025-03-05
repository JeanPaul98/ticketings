package com.acl.ticketingnotification.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Zansouye
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TICKETING_RECETTES")
@Schema(description = "TiketingRecettes: Enregistrement des paiements dans la bdd du PAL")
public class TicketingRecettes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_recette_seq")
    @SequenceGenerator(sequenceName = "ticketing_recette_seq", allocationSize = 1, name = "ticketing_recette_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE_TICKET", unique = true)
    @NotNull
    private String codeTicket;

    @NotNull
    private double montant;

    @NotNull
    @Column(unique = true)
    private String reference;

    @Column(name = "TRANSACTION_ID")
    @NotNull
    private String transactionID;

    private String telephone;

    @NotNull
    private String operateur;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_PAIEMENT")
    @NotNull
    private Date datePaiement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketingRecettes that = (TicketingRecettes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
