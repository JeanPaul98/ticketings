package com.acl.ticketinggateway.gatewayService.paiement;

import com.acl.ticketinggateway.request.EspecePaymentRequest;
import com.acl.ticketinggateway.request.TicketValidationRequest;
import com.acl.ticketinggateway.service.paiement.espece.PaiementEspeceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GatewayPaiementEspeceService {

    final Logger logger = LoggerFactory.getLogger(GatewayPaiementEspeceService.class);

    private final PaiementEspeceService paiementEspeceService;

    public GatewayPaiementEspeceService(PaiementEspeceService paiementEspeceService) {
        this.paiementEspeceService = paiementEspeceService;
    }

    public ResponseEntity<?> cash(EspecePaymentRequest request) {
        return paiementEspeceService.cash(request);
    }

    public ResponseEntity<?> validateTicket(TicketValidationRequest request){
        return paiementEspeceService.validateTicket(request);
    }
}
