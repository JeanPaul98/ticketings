package com.acl.ticketingnotification.services;

import com.acl.ticketingnotification.dto.TicketingRecettesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Zansouye
 */
@Service
public interface TicketingRecettesService {
    ResponseEntity<?>insertPaiement(TicketingRecettesDto ticketingRecettesDto);
}
