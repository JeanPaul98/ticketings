/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acl.ticketingnotification.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Zansouye
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiResponseModel {

    private String status;

    private String message;

    private Object result;

    public ApiResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
