package com.acl.pvoadministration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    String username;
    String password;
    String nom;
    String prenom;
    String telephone;
    String fonction;
    String  roles;
}