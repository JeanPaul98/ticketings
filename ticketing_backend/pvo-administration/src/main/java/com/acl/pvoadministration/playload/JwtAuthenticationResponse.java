/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acl.pvoadministration.playload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jean Paul
 */
@Data
@Getter
@Setter

public class JwtAuthenticationResponse {

    private String accessToken;
    
    private String tokenType = "Bearer";
    
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public JwtAuthenticationResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType= tokenType;
    }
    
    public JwtAuthenticationResponse(String accessToken, String tokenType, String username) {
        this.accessToken = accessToken;
        this.tokenType= tokenType;
        this.username= username;
    }

    public JwtAuthenticationResponse(String accessToken, String tokenType, String username, String firstName, String lastName, String phoneNumber) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
