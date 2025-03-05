package com.acl.ticketingpvoespece.model;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "ticketing_prestation", schema = "sipedba")
@Schema(description = "Prestation")
public class Prestation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_prestation_seq")
    @SequenceGenerator(sequenceName = "ticketing_prestation_seq", allocationSize = 1, name = "ticketing_prestation_seq")
    private Long id;

    private String designation;

    @Column(name = "PERSONNE_PHYSIQUE")
    private Boolean personnePhysique;

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
        Prestation that = (Prestation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
