package com.acl.ticketingpvocoris.model;

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
@Table(name = "ticketing_type_prestation", schema = "sipedba")
@Schema(description = "TypePrestation: ")
public class TypePrestation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_type_prestation_seq")
    @SequenceGenerator(sequenceName = "ticketing_type_prestation_seq", allocationSize = 1, name = "ticketing_type_prestation_seq")
    private Long id;

    private String designation;

    @Column(name = "COMPTE_ACREDITE")
    private String compteAcredite;

    @Column(name = "PRIX_UNITAIRE")
    private double prixUnitaire;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "TICKETING_PRESTATION_ID")
    private Prestation prestation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePrestation that = (TypePrestation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
