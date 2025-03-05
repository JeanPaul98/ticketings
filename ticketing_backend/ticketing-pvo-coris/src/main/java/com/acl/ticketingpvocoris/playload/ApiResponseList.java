/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acl.ticketingpvocoris.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Jean Paul
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiResponseList {

    private String status;

    private String message;

    private List<?> results = new ArrayList<>();
    
    public ApiResponseList( String status, String message){
        this.message=message;
        this.status=status;
    }

}
