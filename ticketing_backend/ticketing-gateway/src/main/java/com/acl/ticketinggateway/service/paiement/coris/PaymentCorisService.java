package com.acl.ticketinggateway.service.paiement.coris;

import com.acl.ticketinggateway.playload.ApiResponseModel;
import com.acl.ticketinggateway.playload.CorisPaymentRequest;
import com.acl.ticketinggateway.playload.UserRequest;
import com.acl.ticketinggateway.request.PaiementRequest;
import com.acl.ticketinggateway.request.PaiementRequestOtp;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ticketing-pvo-coris-money")
public interface PaymentCorisService {

    @PostMapping("api/paiements/coris")
    @Retry(name = "ticketing-pvo-coris-money")
    @CircuitBreaker(name = "ticketing-pvo-coris-money", fallbackMethod = "getAuthentication")
    ResponseEntity<?> payment(PaiementRequest paiementRequest);

    @PostMapping("api/paiements/checkotp")
    @Retry(name = "ticketing-pvo-coris-money")
    @CircuitBreaker(name = "ticketing-pvo-coris-money", fallbackMethod = "getAuthentication")
    ResponseEntity<?> checkOtp(PaiementRequestOtp paiementRequestOtp);

    @PostMapping("api/paiements/checkotp/pesage")
    @Retry(name = "ticketing-pvo-coris-money")
    @CircuitBreaker(name = "ticketing-pvo-coris-money", fallbackMethod = "getAuthentication")
    ResponseEntity<?> checkOtpPesage(PaiementRequestOtp paiementRequestOtp);

    default ResponseEntity<?> getAuthentication(Throwable exception) {
        System.out.println("Message " + exception.getMessage());
        return new ResponseEntity<>(new ApiResponseModel(HttpStatus.NOT_FOUND.name(),
                "Opération échoué"), HttpStatus.NOT_FOUND);
    }
}
