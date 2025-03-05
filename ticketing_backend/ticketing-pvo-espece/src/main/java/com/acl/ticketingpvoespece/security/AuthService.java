package com.acl.ticketingpvoespece.security;


import com.acl.ticketingpvoespece.playload.LoginRequest;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<?> connexionTpe(LoginRequest loginRequest);

    ResponseEntity<?> connexionWeb(LoginRequest loginRequest);


}
