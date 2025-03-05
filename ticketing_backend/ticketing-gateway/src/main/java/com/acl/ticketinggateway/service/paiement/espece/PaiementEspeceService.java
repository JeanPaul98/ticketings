package com.acl.ticketinggateway.service.paiement.espece;

import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.request.EspecePaymentRequest;
import com.acl.ticketinggateway.request.TicketValidationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ticketing-pvo-espece")
public interface PaiementEspeceService {
    @PostMapping("/api/paiement/cash")
    @Retry(name = "ticketing-pvo-espece")
    @CircuitBreaker(name = "ticketing-pvo-espece", fallbackMethod = "getAuthentication")
    ResponseEntity<?> cash(EspecePaymentRequest request);

    @PostMapping("/api/ticket/code")
    @Retry(name = "ticketing-pvo-espece")
    @CircuitBreaker(name = "ticketing-pvo-espece", fallbackMethod = "getAuthentication")
    ResponseEntity<?> validateTicket(TicketValidationRequest request);


    default ResponseEntity<?> getAuthentication( Throwable exception) {
        System.out.println("Message "+ exception.getMessage());
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Opération échoué"), HttpStatus.NOT_FOUND);
    }

}
