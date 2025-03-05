package com.acl.ticketingauth.service;

import com.acl.ticketingauth.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Zansouyé
 */
@Service
public interface AuthUserService {
    ResponseEntity<?> createUser(RegisterDto request);
}
