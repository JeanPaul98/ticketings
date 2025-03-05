package com.acl.ticketingpvocoris.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class CaissierDto {

    String username;

    String password;

    String name;

    String prenom;

    String telephone;

    String fonction;
}