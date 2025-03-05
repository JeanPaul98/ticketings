package com.acl.ticketingpvocoris.services;


import com.acl.ticketingpvocoris.playload.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<?> connexionTpe(LoginRequest loginRequest);

    ResponseEntity<?> connexionWeb(LoginRequest loginRequest);


}
