package com.acl.ticketingpvoespece.services;

import com.acl.ticketingpvoespece.dto.TicketValidationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TicketValidationService {
    ResponseEntity<?> validateTicket(TicketValidationDto request);

}
