package com.acl.pvoadministration.services;

import com.acl.pvoadministration.playload.TicketValidationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TicketValidationService {
    ResponseEntity<?> validateTicket(TicketValidationRequest request);

}
