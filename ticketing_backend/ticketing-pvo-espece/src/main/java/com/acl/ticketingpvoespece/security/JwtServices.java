package com.acl.ticketingpvoespece.security;

import com.acl.ticketingpvoespece.model.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtServices {

    String extractUserName(String token);
    String getUserIdFromJWT(String token);
    String generateToken(User user);
    boolean isValidToken(String token);
}
