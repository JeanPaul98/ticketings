package com.acl.ticketingpvocoris.services.coris;


import com.acl.ticketingpvocoris.dto.PaiementDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PaiementService {

    ResponseEntity<?> initialisationTransaction(PaiementDto paiementDto) throws Exception;
}
