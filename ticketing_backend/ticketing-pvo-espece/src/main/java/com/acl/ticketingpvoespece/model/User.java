package com.acl.ticketingpvoespece.model;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ticketing_user", schema = "sipedba")
@Schema(description = "User")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ticketing_user_seq")
    @SequenceGenerator(sequenceName = "ticketing_user_seq", allocationSize = 1, name = "ticketing_user_seq")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String nom;

    private String prenom;

    @NotBlank
    @NotNull
    private String telephone;

    private String fonction;

    private boolean actif;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CONNEXION")
    private Date dateConnexion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_DECONNEXION")
    private Date dateDeconnexion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticketing_user_role",schema = "sipedba",
            joinColumns = @JoinColumn(name = "ticketing_user_id"),
            inverseJoinColumns = @JoinColumn(name = "ticketing_role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User USER = (User) o;
        return Objects.equals(id, USER.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
