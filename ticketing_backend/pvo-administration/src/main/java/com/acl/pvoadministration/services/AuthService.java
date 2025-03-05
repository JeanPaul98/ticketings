package com.acl.pvoadministration.services;


import com.acl.pvoadministration.playload.LoginRequest;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<?> connexionTpe(LoginRequest loginRequest);

    ResponseEntity<?> connexionWeb(LoginRequest loginRequest);


}
