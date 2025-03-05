package com.acl.ticketingauth.security;


import com.acl.ticketingauth.model.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtServices {

    String extractUserName(String token);

    String getUserIdFromJWT(String token);

    String generateToken(User user);

    boolean isValidToken(String token);
}
