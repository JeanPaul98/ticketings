/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acl.pvoadministration.security.impl;


import com.acl.pvoadministration.model.Role;
import com.acl.pvoadministration.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @author Jean Paul
 */
@AllArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private final Logger logger = LoggerFactory.getLogger(UserPrincipal.class);

    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String password;

    private User userRole;

    private Set<Role> roles;

    public UserPrincipal(long id, String username, String nom, String prenom, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.roles = roles;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getNom(),
                user.getPrenom(),
                user.getPassword(),
                user.getRoles()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("User principal Autorithy : ");
        logger.info("User Role : " + roles);
        userRole = new User();
        userRole.setRoles(roles);
        logger.info("User principal: " + userRole);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: userRole.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
