package com.acl.ticketingauth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ticketing_role", schema = "sipedba")
@Schema(description = "Role des utilisateurs")
@ToString
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_role_seq")
    @SequenceGenerator(sequenceName = "ticketing_role_seq", allocationSize = 1, name = "ticketing_role_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "ROLE_DESC")
    private String roleDesc;

    private boolean actif;

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
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
