package com.acl.ticketingauth.service;


import com.acl.ticketingauth.requests.LoginRequest;
import com.acl.ticketingauth.dto.RegisterDto;
import org.springframework.http.ResponseEntity;


/**
 * @author Zansouyé
 */
public interface AuthService {
    ResponseEntity<?> connexionTpe(LoginRequest loginRequest);

    ResponseEntity<?> connexionWeb(LoginRequest loginRequest);




}
